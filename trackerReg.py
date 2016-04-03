#Requires zeroconf library
import socket
from time import sleep
from zeroconf import ServiceInfo, Zeroconf

# Expect all input of type string (exceptions listed) #
# Port,weight and priority of type int #
def createService(typeURL="http", name, address, port, weight=0, priority=0):
    
    zeroconf = Zeroconf()
    
    # Look up info's __init__ in python-zeroconf's documentation #
    info = ServiceInfo(typeURL, name, address, socket.inet_aton(port), weight, priority)
    # Server is supported but not compulsory, set server as inputted name #
    
    zeroconf.register_service(info)
    
    try:
        while True: sleep(0.1)
    except KeyboardInterrupt:
        zeroconf.unregister_service(info)
        print("Unregistered")
        zeroconf.close()
    print("Service registered")
    zeroconf.close()

def serviceStatus(typeURL,name):
    
    zeroconf = Zeroconf()
    
    info = zeroconf.get_service_info(typeURL, name)
    
    # If service registered #
    if info:
        return (socket.inet_ntoa(info.address), info.port)
    # No named service registered #
    else:
        print("Service doesn't exist")
    zeroconf.close()
    