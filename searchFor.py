import pyasn
import sys

addr = sys.argv[1:]  # 获取到的python的参数
asndb = pyasn.pyasn('./IPASNv6.dat')
straddr = str(addr).split("'")[1]
a, b = asndb.lookup(straddr)
print(straddr + ";;" + str(a) + ";;" + str(b))