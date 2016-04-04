from websocket import create_connection
from trackerReg import serviceStatus
import socket


serverIP = str(serviceStatus()[0])
#serverIP = "localhost"
def progressSender(progress):
    ws = create_connection("ws://" + serverIP + ":8080/events/")
    ws.send(str(socket.gethostname() + "," + str(progress)))
    #if progress == "Done":
    #    result = ws.recv()

    ws.close()
