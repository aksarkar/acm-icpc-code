import random

decisions = ['y', 'n']
bills = range(1, 101)

with open('major.in', 'a') as f:
    f.write('%d %d\n' % (100, 500))
    for i in xrange(500):
        decisions_ = [(b, random.choice(decisions)) for b in
                      random.sample(bills, random.randrange(1, 5))]
        f.write('%d %s\n' % (len(decisions_),
                             ' '.join(['%d %s' % (b, d) for (b, d) in
                                      decisions_])))
    f.write('0 0\n')
                             
