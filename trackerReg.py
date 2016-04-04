#Requires zeroconf library
import socket
from time import sleep
from zeroconf import ServiceInfo, Zeroconf


# Expect all input of type string (exceptions listed) #
# Port,weight and priority of type int #
def createService():

    zeroconf = Zeroconf()
    # Look up info's __init__ in python-zeroconf's documentation #
    info = ServiceInfo("_http._tcp.local.", "Takiyaki._http._tcp.local.", socket.inet_aton(socket.gethostbyname(socket.gethostname())), 8080,0,0,socket.gethostname() + ".local.")
    # Server is supported but not compulsory, set server as inputted name #
    print "Registered Service [" + info.name + "]"
    zeroconf.register_service(info)

    try:
        while True: sleep(0.1)
    except KeyboardInterrupt:
        zeroconf.unregister_service(info)
        print("Unregistered")
        zeroconf.close()
    print("Service registered")
    zeroconf.close()

def serviceStatus():

    zeroconf = Zeroconf()

    info = zeroconf.get_service_info("_http._tcp.local.", "Takiyaki._http._tcp.local.")

    # If service registered #
    if info:
        return (socket.inet_ntoa(info.address), info.port)
    # No named service registered #
    else:
        print("Service doesn't exist")
    zeroconf.close()
