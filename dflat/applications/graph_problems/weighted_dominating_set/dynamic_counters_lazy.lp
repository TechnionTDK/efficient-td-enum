%dflat: --tables -e vertex -e edge -d td -n semi --default-join --lazy
weight(X,1) :- vertex(X).
#external childItem(in(X)) : childNode(N), bag(N,X).
#external childAuxItem(dominated(X)) : childNode(N), bag(N,X).
#external childCounter(1..N) : maxSumOfWeights(N).
#show childItem/1.
#show childAuxItem/1.
{ item(in(X)) : introduced(X) }.
item(in(X)) :- not removed(X), childItem(in(X)).
auxItem(dominated(X)) :- not removed(X), childAuxItem(dominated(X)).
auxItem(dominated(Y)) :- item(in(X)), current(X), current(Y), edge(X,Y).

:- removed(X), not childItem(in(X)), not childAuxItem(dominated(X)).

counterInc(cost,W,X) :- introduced(X), item(in(X)), weight(X,W).
currentCounterInc(cost,W,X) :- introduced(X), item(in(X)), weight(X,W).
currentCounterInc(cost,-W,X) :- removed(X), childItem(in(X)), weight(X,W).

#show item/1.
#show auxItem/1.
#show counterInc/1.
#show currentCounterInc/1.
