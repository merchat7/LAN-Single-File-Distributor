import libtorrent as lt
import time,sys


def maketorrent():
    fs = lt.file_storage()
    lt.add_files(fs, "test.mp3")
    t = lt.create_torrent(fs)
    t.add_tracker("udp://tracker.openbittorrent.com:80/announce", 0)
    t.set_creator('A bunny')
    lt.set_piece_hashes(t, ".")
    torrent = t.generate()
    f = open("mytorrent.torrent", "wb")
    f.write(lt.bencode(torrent))
    f.close()

maketorrent()

def download():
    ses = lt.session()
    ses.listen_on(6881, 6891)

    e = lt.bdecode(open("mytorrent.torrent", 'rb').read())
    info = lt.torrent_info(e)
    h = ses.add_torrent({'ti': info, 'save_path': 'C:\Users\merchat7\Desktop'})
    print 'starting', h.name()

    s = h.status()
    state_str = ['queued', 'checking', 'downloading metadata', \
                 'downloading', 'finished', 'seeding', 'allocating', 'checking fastresume']

    while (not h.is_seed()):
       print '\r%.2f%% complete (down: %.1f kb/s up: %.1f kB/s peers: %d) %s' % \
          (s.progress * 100, s.download_rate / 1000, s.upload_rate / 1000, \
          s.num_peers, state_str[s.state]),
       sys.stdout.flush()

       time.sleep(1)

    print h.name(), 'complete'

    while h.is_seed():
        print('\r%.2f%% complete (down: %.1f kb/s up: %.1f kB/s peers: %d) %s' % \
          (s.progress * 100, s.download_rate / 1000, s.upload_rate / 1000, s.num_peers, state_str[s.state]))
        sys.stdout.flush()
        time.sleep(1)

download()