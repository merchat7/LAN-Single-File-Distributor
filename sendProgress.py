from websocket import create_connection
from trackerReg import serviceStatus
import socket


info = serviceStatus()
if info:
    serverIP = info[0]
def progressSender(progress):
    ws = create_connection("ws://" + serverIP + ":8080/events/")
    ws.send(str(socket.gethostname() + "," + str(progress)))
    #if progress == "Done":
    #    result = ws.recv()

    ws.close()
