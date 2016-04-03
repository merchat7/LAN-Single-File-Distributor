import os
from TRclient import maketorrent,download

if __name__ == "__main__":
   print "Welcome to server initialization!"
   print "(1) New torrent or (2) Seed existing torrent? Type 1 or 2:"
   while True:
       userInput = raw_input()
       if userInput == "1":
          print "Please put your source file into the same directory where this .py is run"
          print "Now please type in the name of the file you want to distribute (e.g. happy.mp3):"
          while True:
            sourceFile = raw_input()
            if os.path.isfile(sourceFile):
                break
            else:
                print "File doesn't exist, please type the name again"
          maketorrent(sourceFile)
          break
       elif userInput == "2":
          break
       else:
          print "Please type either 1 or 2:"
           
   print "Starting tracker and server..."
   os.system("StartAllSVRServ.bat")
   try:
       download("server")
   except:
        print "Ensure there is a .torrent file"
