import sys
import time
from netifaces import interfaces, ifaddresses, AF_INET
from os.path import expanduser

import libtorrent as lt


#from http://stackoverflow.com/questions/270745/how-do-i-determine-all-of-my-ip-addresses-when-i-have-multiple-nics
def ip4_addresses():
    ip_list = []
    for interface in interfaces():
        for link in ifaddresses(interface).get(AF_INET, ()):
            ip_list.append(link['addr'])
    return ip_list


def maketorrent(source):
    print "Making .torrent file for " + source + "..."
    fs = lt.file_storage()
    lt.add_files(fs, source)
    t = lt.create_torrent(fs)
    for ip in ip4_addresses():
        trAddress = "http://" + ip + ":6969/announce"
        t.add_tracker(trAddress,0)
    t.set_creator('A bunny')
    lt.set_piece_hashes(t, ".")
    torrent = t.generate()
    f = open("t.torrent", "wb")
    f.write(lt.bencode(torrent))
    f.close()
    print "Finished making .torrent file"

def download(clientType):
    if clientType == "server":
        dest = ""
    else:
        dest = expanduser("~") + "\Downloads"

    ses = lt.session()
    ses.listen_on(6881, 6901) #keep trying to bind to a port till set value

    torrentContent = lt.bdecode(open("t.torrent", 'rb').read())
    info = lt.torrent_info(torrentContent)
    if clientType == "server":
        h = ses.add_torrent({'ti': info, 'save_path': dest, 'seed_mode': True})
    else:
        h = ses.add_torrent({'ti': info, 'save_path': dest})

    while (not h.is_seed()):
       print "Starting download..."
       s = h.status()
       state_str = ['queued', 'checking', 'downloading metadata', \
                     'downloading', 'finished', 'seeding', 'allocating', 'checking fastresume']
       print '\r%.2f%% complete (down: %.1f kb/s up: %.1f kB/s peers: %d) %s' % \
          (s.progress * 100, s.download_rate / 1000, s.upload_rate / 1000, \
          s.num_peers, state_str[s.state]),
       sys.stdout.flush()

       time.sleep(1)

    if clientType != "server":
        print "Download completed"
    print "Seeding...\nCtrl+C to stop"

    while h.is_seed():
        time.sleep(1) #keep process from getting garbage collected?