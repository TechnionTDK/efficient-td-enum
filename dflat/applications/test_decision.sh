#!/bin/bash

numInstances=100

if [[ -z "$instanceGen" || -z "$dflatArguments" || -z "$monolithicEncoding" ]]; then
	echo "Environment variables not set"
	exit 1
fi

for instance in $(seq 1 $numInstances); do
	tdId=$RANDOM

	instance=$(mktemp)
	trap "rm -f $instance" EXIT

	$instanceGen $tdId > $instance || exit

	gringo $monolithicEncoding $instance 2>/dev/null | clasp -q >/dev/null
	claspExit=$?
	dflat $dflatArguments --depth 0 --tdId $tdId < $instance >/dev/null
	dflatExit=$?

	[ $claspExit -ne 30 ] || claspExit=10

	if [ $claspExit -ne $dflatExit ]; then
		cp $instance mismatch${tdId}.lp
		echo
		echo "Mismatch for tdId $tdId (dflat: ${dflatExit}, clasp: ${claspExit})"
		exit 1
	else
#		echo -n .
		echo -n "$dflatExit "
	fi

	# remove temp file
	rm -f $instance
	trap - EXIT
done
echo
