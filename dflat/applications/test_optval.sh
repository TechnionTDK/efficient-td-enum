#!/bin/bash

numInstances=100

if [[ -z "$instanceGen" || -z "$dflatArguments" || -z "$monolithicEncoding" ]]; then
	echo "Environment variables not set"
	exit 1
fi

for instance in $(seq 1 $numInstances); do
	tdId=$RANDOM

	instance=$(mktemp)
	claspOptValFile=$(mktemp)
	dflatOptValFile=$(mktemp)
	trap "rm -f $instance $claspOptValFile $dflatOptValFile" EXIT

	$instanceGen $tdId > $instance 2>/dev/null || exit

	gringo $monolithicEncoding $instance | clasp -q 0 | awk '/^Optimization :/ { print $3 }' > $claspOptValFile
	claspExit=${PIPESTATUS[1]}
	claspOptVal=$(<$claspOptValFile)

	dflat $dflatArguments --depth 0 --tdId $tdId < $instance | tail -n1 | awk -F " |)" '{print $3}' > $dflatOptValFile
	dflatExit=${PIPESTATUS[0]}
	dflatOptVal=$(<$dflatOptValFile)

	[ $claspExit -ne 30 ] || claspExit=10

	if [ $claspExit -ne $dflatExit ]; then
		cp $instance mismatch${tdId}.lp
		echo
		echo "Exit code mismatch for tdId $tdId (dflat: ${dflatExit}, clasp: ${claspExit})"
		exit 1
	elif [ $claspOptVal -ne $dflatOptVal ]; then
		cp $instance mismatch${tdId}.lp
		echo
		echo "Optimum value mismatch for tdId $tdId (dflat: ${dflatOptVal}, clasp: ${claspOptVal})"
		exit 2
	else
#		echo -n "$dflatExit "
		echo -n "$dflatOptVal "
	fi

	# remove temp file
	rm -f $instance $claspOptValFile $dflatOptValFile
	trap - EXIT
done
echo
