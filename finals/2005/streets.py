import random

x = set()
y = set()
with open('streets.in', 'a') as f:
    f.write('%d\n' % (500,))
    for i in xrange(500):
        horiz = random.random() < .5
        if horiz:
            y1 = random.randrange(0, 1000)
            y2 = y1
            y.add(y1)
            x1 = random.randrange(0, 1000)
            x2 = random.randrange(0, 1000)
        else:
            y1 = random.randrange(0, 1000)
            y2 = random.randrange(0, 1000)
            x1 = random.randrange(0, 1000)
            x2 = x1
            x.add(x1)
        f.write('%d %d %d %d\n' % (x1, y1, x2, y2))
    xh = random.randrange(0, 1000)
    while xh in x:
        xh = random.randrange(0, 1000)
    xu = random.randrange(0, 1000)
    while xu in x:
        xu = random.randrange(0, 1000)
    yh = random.randrange(0, 1000)
    while yh in y:
        yh = random.randrange(0, 1000)
    yu = random.randrange(0, 1000)
    while yu in y:
        yu = random.randrange(0, 1000)
    f.write('%d %d %d %d\n' % (xh, yh, xu, yu))
    f.write('%d\n' % (0,))

