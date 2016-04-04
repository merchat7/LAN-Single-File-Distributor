from trackerReg import serviceStatus
import webbrowser

info = serviceStatus()
address = "http://" + info[0]+":8080"
webbrowser.open(address)