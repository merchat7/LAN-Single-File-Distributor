import os
from TRclient import maketorrent,download

if __name__ == "__main__":
   print "Welcome to server initialization!"
   print "First, put your source file into the same directory where this .py is run"
   print "Now please type in the name of the file you want to distribute (e.g. happy.mp3):"
   sourceFile = raw_input()
   maketorrent(sourceFile)
   print "Starting tracker and server..."
   os.system("StartAllSVRServ.bat")
   print "Begin seeding"
   download("server")