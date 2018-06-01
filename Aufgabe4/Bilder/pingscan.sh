#!/bin/sh
# ping scanner

for i in `seq 1 254`
do
	ping -c 1 10.1.1.$i
	echo pinging 10.1.1.$i
done
