#!/bin/bash

numInstances=100

if [[ -z "$instanceGen" || -z "$dflatArguments" || -z "$monolithicEncoding" ]]; then
	echo "Environment variables not set"
	exit 1
fi

for instance in $(seq 1 $numInstances); do
	tdId=$RANDOM

	instance=$(mktemp)
	claspCountFile=$(mktemp)
	dflatCountFile=$(mktemp)
	trap "rm -f $instance $claspCountFile $dflatCountFile" EXIT

	$instanceGen $tdId > $instance || exit

	gringo $monolithicEncoding $instance | clasp -q 0 | awk '/Models/ { print $3 }' > $claspCountFile
	claspExit=${PIPESTATUS[1]}
	claspCount=$(<$claspCountFile)
	
	dflat $dflatArguments --depth 0 --tdId $tdId < $instance | tail -n1 | sed 's/\[\([0-9]*\)\]/\1/g' > $dflatCountFile
	dflatExit=${PIPESTATUS[0]}
	dflatCount=$(<$dflatCountFile)

	[ $claspExit -ne 30 ] || claspExit=10

	if [ $claspExit -ne $dflatExit ]; then
		cp $instance mismatch${tdId}.lp
		echo
		echo "Exit code mismatch for tdId $tdId (dflat: ${dflatExit}, clasp: ${claspExit})"
		exit 1
	elif [ $claspCount -ne $dflatCount ]; then
		cp $instance mismatch${tdId}.lp
		echo
		echo "Count mismatch for tdId $tdId (dflat: ${dflatCount}, clasp: ${claspCount})"
		exit 2
	else
#		echo -n "$dflatExit "
		echo -n "$dflatCount "
	fi

	# remove temp file
	rm -f $instance $claspCountFile $dflatCountFile
	trap - EXIT
done
echo
