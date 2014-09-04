from datetime import datetime

__author__ = 'Patrick'

expression = "x + y < 2*z";
func = eval(" ( lambda x, y, z: " + expression + ")")

max = 500

start = datetime.now()
for x in range(max):
    for y in range(max):
        for z in range(max):
            func(x, y, z)

c = datetime.now() - start
print c.microseconds/1000, "ms for", max**3, "iterations"

# 1.29