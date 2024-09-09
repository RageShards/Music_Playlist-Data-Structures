package music;

import java.util.*;

/**
 * This class represents a library of song playlists.
 *
 * An ArrayList of Playlist objects represents the various playlists
 * within one's library.
 * 
 * @author Jeremy Hui
 * @author Vian Miranda
 */
public class PlaylistLibrary {

    private ArrayList<Playlist> songLibrary; // contains various playlists

    /**
     * DO NOT EDIT!
     * Constructor for Library.
     * 
     * @param songLibrary passes in ArrayList of playlists
     */
    public PlaylistLibrary(ArrayList<Playlist> songLibrary) {
        this.songLibrary = songLibrary;
    }

    /**
     * DO NOT EDIT!
     * Default constructor for an empty library.
     */
    public PlaylistLibrary() {
        this(null);
    }

    /**
     * This method reads the songs from an input csv file, and creates a
     * playlist from it.
     * Each song is on a different line.
     * 
     * 1. Open the file using StdIn.setFile(filename);
     * 
     * 2. Declare a reference that will refer to the last song of the circular
     * linked list.
     * 
     * 3. While there are still lines in the input file:
     * 1. read a song from the file
     * 2. create an object of type Song with the song information
     * 3. Create a SongNode object that holds the Song object from step 3.2.
     * 4. insert the Song object at the END of the circular linked list (use the
     * reference from step 2)
     * 5. increase the count of the number of songs
     * 
     * 4. Create a Playlist object with the reference from step (2) and the number
     * of songs in the playlist
     * 
     * 5. Return the Playlist object
     * 
     * Each line of the input file has the following format:
     * songName,artist,year,popularity,link
     * 
     * To read a line, use StdIn.readline(), then use .split(",") to extract
     * fields from the line.
     * 
     * If the playlist is empty, return a Playlist object with null for its last,
     * and 0 for its size.
     * 
     * The input file has Songs in decreasing popularity order.
     * 
     * DO NOT implement a sorting method, PRACTICE add to end.
     * 
     * @param filename the playlist information input file
     * @return a Playlist object, which contains a reference to the LAST song
     *         in the ciruclar linkedlist playlist and the size of the playlist.
     */
    public Playlist createPlaylist(String filename) {

        // WRITE YOUR CODE HERE
        Playlist cs = new Playlist();
        StdIn.setFile(filename);

        if (StdIn.isEmpty()) {
            Playlist empty = new Playlist(null, 0);
            return empty;
        }
        SongNode first = new SongNode();
        SongNode last = new SongNode();
        String firstL = StdIn.readLine();
        String[] read = firstL.split(",");
        int year1 = Integer.parseInt(read[2]);
        int pop1 = Integer.parseInt(read[3]);
        first.setSong(new Song(read[0], read[1], year1, pop1));
        first.setNext(first);
        last = first;
        int sCounter = 1;

        while (StdIn.hasNextLine()) {

            String[] extract = StdIn.readLine().split(",");
            int year = Integer.parseInt(extract[2]);
            int pop = Integer.parseInt(extract[3]);
            Song store = new Song(extract[0], extract[1], year, pop);
            SongNode currentS = new SongNode(store, first);
            last.setNext(currentS);
            last = currentS;
            sCounter = sCounter + 1;

        }
        cs.setLast(last);
        cs.setSize(sCounter);

        return cs; // update this line with your returning Playlist
    }

    /**
     * ****DO NOT**** UPDATE THIS METHOD
     * This method is already implemented for you.
     * 
     * Adds a new playlist into the song library at a certain index.
     * 
     * 1. Calls createPlayList() with a file containing song information.
     * 2. Adds the new playlist created by createPlayList() into the songLibrary.
     * 
     * Note: initialize the songLibrary if it is null
     * 
     * @param filename      the playlist information input file
     * @param playlistIndex the index of the location where the playlist will
     *                      be added
     */
    public void addPlaylist(String filename, int playlistIndex) {

        /* DO NOT UPDATE THIS METHOD */

        if (songLibrary == null) {
            songLibrary = new ArrayList<Playlist>();
        }
        if (playlistIndex >= songLibrary.size()) {
            songLibrary.add(createPlaylist(filename));
        } else {
            songLibrary.add(playlistIndex, createPlaylist(filename));
        }
    }

    /**
     * ****DO NOT**** UPDATE THIS METHOD
     * This method is already implemented for you.
     * 
     * It takes a playlistIndex, and removes the playlist located at that index.
     * 
     * @param playlistIndex the index of the playlist to remove
     * @return true if the playlist has been deleted
     */
    public boolean removePlaylist(int playlistIndex) {
        /* DO NOT UPDATE THIS METHOD */

        if (songLibrary == null || playlistIndex >= songLibrary.size()) {
            return false;
        }

        songLibrary.remove(playlistIndex);

        return true;
    }

    /**
     * 
     * Adds the playlists from many files into the songLibrary
     * 
     * 1. Initialize the songLibrary
     * 
     * 2. For each of the filenames
     * add the playlist into songLibrary
     * 
     * The playlist will have the same index in songLibrary as it has in
     * the filenames array. For example if the playlist is being created
     * from the filename[i] it will be added to songLibrary[i].
     * Use the addPlaylist() method.
     * 
     * @param filenames an array of the filenames of playlists that should be
     *                  added to the library
     */
    public void addAllPlaylists(String[] filenames) {
        songLibrary = new ArrayList<Playlist>();
        for (String filename : filenames) {
            Playlist storeP = createPlaylist(filename);
            songLibrary.add(storeP);
        }
    }

    /**
     * This method adds a song to a specified playlist at a given position.
     * 
     * The first node of the circular linked list is at position 1, the
     * second node is at position 2 and so forth.
     * 
     * Return true if the song can be added at the given position within the
     * specified playlist (and thus has been added to the playlist), false
     * otherwise (and the song will not be added).
     * 
     * Increment the size of the playlist if the song has been successfully
     * added to the playlist.
     * 
     * @param playlistIndex the index where the playlist will be added
     * @param position      the position inthe playlist to which the song
     *                      is to be added
     * @param song          the song to add
     * @return true if the song can be added and therefore has been added,
     *         false otherwise.
     */
    public boolean insertSong(int playlistIndex, int position, Song song) {
        Playlist storeP = songLibrary.get(playlistIndex);
        if (position <= 0 || position > storeP.getSize() + 1) {
            return false;
        }

        if (storeP.getSize() == 0) {
          SongNode testNode = new SongNode(song, null);
          testNode.setNext(testNode);
          storeP.setLast(testNode);
          return true;
        }

        if (position == storeP.getSize() + 1) {
          SongNode test = new SongNode(song, null);
          test.setNext(storeP.getLast().getNext());
          storeP.getLast().setNext(test);
          storeP.setLast(test);
          return true;
        }

    
        SongNode ptr = storeP.getLast();
        for (int i = 1; i < position; i++) {
            ptr = ptr.getNext();
        }
        SongNode temp = ptr.getNext();
        ptr.setNext(new SongNode(song, temp));
        storeP.setSize(storeP.getSize()+1);
        return true; // update the return value
    }


    /**
     * This method removes a song at a specified playlist, if the song exists.
     *
     * Use the .equals() method of the Song class to check if an element of
     * the circular linkedlist matches the specified song.
     * 
     * Return true if the song is found in the playlist (and thus has been
     * removed), false otherwise (and thus nothing is removed).
     * 
     * Decrease the playlist size by one if the song has been successfully
     * removed from the playlist.
     * 
     * @param playlistIndex the playlist index within the songLibrary where
     *                      the song is to be added.
     * @param song          the song to remove.
     * @return true if the song is present in the playlist and therefore has
     *         been removed, false otherwise.
     */
        public boolean removeSong(int playlistIndex, Song song) {
            Playlist playlist = songLibrary.get(playlistIndex);
            SongNode ptr = playlist.getLast();
            int ptrCount = 0;
            if(playlist.getSize() == 0){
                return false;
            }
            if ((playlist.getSize() == 1) && (playlist.getLast().getSong().equals(song))) {
                playlist.setLast(null);
                playlist.setSize(0);
                return true;
              }
          
            while (true) {
                if (ptrCount != playlist.getSize() -1 && ptr.getNext().getSong().equals(song)) {
                ptr.setNext(ptr.getNext().getNext());
                playlist.setSize(playlist.getSize()-1);
                return true;
            }
            if (ptrCount == playlist.getSize()-1){
                if(ptr.getNext().getSong().equals(song)){
                    ptr.setNext(ptr.getNext().getNext());
                    playlist.setSize(playlist.getSize()-1);
                    songLibrary.get(playlistIndex).setLast(ptr);
                    return true;
                }
            break;
            }
            ptr = ptr.getNext();
            ptrCount++;
         }
        return false;
    }

    

    /**
     * This method reverses the playlist located at playlistIndex
     * 
     * Each node in the circular linked list will point to the element that
     * came before it.
     * 
     * After the list is reversed, the playlist located at playlistIndex will
     * reference the first SongNode in the original playlist (new last).
     * 
     * @param playlistIndex the playlist to reverse
     */
    public void reversePlaylist(int playlistIndex) {
        // WRITE YOUR CODE HERE
    Playlist storeP = songLibrary.get(playlistIndex);
        int storeSize = storeP.getSize();

        if(storeSize <= 1){
            return;
        }
        SongNode ptr = storeP.getLast().getNext();
    for(int i =0; i < storeP.getSize();i++){
        SongNode next = ptr.getNext();
        ptr.setNext(storeP.getLast());
        storeP.setLast(ptr);
        ptr = next;
    }

    storeP.setLast(ptr);

    }


    /**
     * This method merges two playlists.
     * 
     * Both playlists have songs in decreasing popularity order. The resulting
     * playlist will also be in decreasing popularity order.
     * 
     * You may assume both playlists are already in decreasing popularity
     * order. If the songs have the same popularity, add the song from the
     * playlist with the lower playlistIndex first.
     * 
     * After the lists have been merged:
     * - store the merged playlist at the lower playlistIndex
     * - remove playlist at the higher playlistIndex
     * 
     * 
     * @param playlistIndex1 the first playlist to merge into one playlist
     * @param playlistIndex2 the second playlist to merge into one playlist
     */

    public void mergePlaylists(int playlistIndex1, int playlistIndex2) {
        
        int smallerIndex = Math.min(playlistIndex1, playlistIndex2);
        int largerIndex = Math.max(playlistIndex1, playlistIndex2);
    
        // Retrieve the playlists to be merged
        Playlist playlist1 = songLibrary.get(smallerIndex);
        Playlist playlist2 = songLibrary.get(largerIndex);
        int storesize1 = playlist1.getSize();
        int storesize2 = playlist2.getSize();
        if (storesize1 == 0 && storesize2 == 0) {
            return;
          }
        SongNode endMerge = null;
    
        while (playlist1.getSize() != 0 && playlist2.getSize() != 0) {
          SongNode headL = playlist1.getLast().getNext();
          SongNode headH = playlist2.getLast().getNext();
        Playlist storeMerged;

          if (headL.getSong().getPopularity() >= headH.getSong().getPopularity()) {
              storeMerged = playlist1;
          } 
          else {
              storeMerged = playlist2;
          }
    
          SongNode mergedHead = new SongNode(storeMerged.getLast().getNext().getSong(), null);
    
          // Now add targetHead at the end of the merged playlist (last node is mergedLast)
          if (endMerge == null) {
            endMerge = mergedHead;
            endMerge.setNext(endMerge);
          } 
          else {
            mergedHead.setNext(endMerge.getNext());
            endMerge.setNext(mergedHead);
            endMerge = mergedHead;
          }
    
          // Now delete the song from target
          removeSong(songLibrary.indexOf(storeMerged), mergedHead.getSong());
        }
    
        Playlist checkP;
        if (playlist1.getSize() == 0) {
            checkP = playlist2;
        } 
        else {
            checkP = playlist1;
        }
        SongNode checkPtr = checkP.getLast().getNext();
        for (int i = 0; i < checkP.getSize(); i++) {
          SongNode targetSongNode = new SongNode(checkPtr.getSong(), null);
    
          if (endMerge == null) {
            endMerge = targetSongNode;
            endMerge.setNext(endMerge);
          } 
          else {
            targetSongNode.setNext(endMerge.getNext());
            endMerge.setNext(targetSongNode);
            endMerge = targetSongNode;
          }
    
          checkPtr = checkPtr.getNext();
        }
    
        removePlaylist(songLibrary.indexOf(playlist2));
        playlist1.setLast(endMerge);
        playlist1.setSize(storesize1 + storesize2);
      }
    

    /**
     * This method shuffles a specified playlist using the following procedure:
     * 
     * 1. Create a new playlist to store the shuffled playlist in.
     * 
     * 2. While the size of the original playlist is not 0, randomly generate a
     * number
     * using StdRandom.uniformInt(1, size+1). Size contains the current number
     * of items in the original playlist.
     * 
     * 3. Remove the corresponding node from the original playlist and insert
     * it into the END of the new playlist (1 being the first node, 2 being the
     * second, etc).
     * 
     * 4. Update the old playlist with the new shuffled playlist.
     * 
     * @param index the playlist to shuffle in songLibrary
     */
    public void shufflePlaylist(int playlistIndex) {
        Playlist orgPlaylist = songLibrary.get(playlistIndex);
        int originalPlaylistSize = orgPlaylist.getSize();
        SongNode lastShuffled = null;

    while (orgPlaylist.getSize() > 0) {
    int randomNumber = StdRandom.uniformInt(orgPlaylist.getSize() + 1);

    SongNode nodeToShuffle = orgPlaylist.getLast().getNext();
    int count = 1;
        while (count < randomNumber + 1) {
        nodeToShuffle = nodeToShuffle.getNext();
        count++;
        }

        SongNode shuffledSongNode = new SongNode(nodeToShuffle.getSong(), null);
     if (lastShuffled == null) {
        lastShuffled = shuffledSongNode;
        lastShuffled.setNext(lastShuffled);
    } 
    else {
        shuffledSongNode.setNext(lastShuffled.getNext());
        lastShuffled.setNext(shuffledSongNode);
        lastShuffled = shuffledSongNode;
    }

    // Delete nodeToShuffle
    removeSong(playlistIndex, nodeToShuffle.getSong());
}

    orgPlaylist.setLast(lastShuffled);
    orgPlaylist.setSize(originalPlaylistSize);

}

    /**
     * This method sorts a specified playlist using linearithmic sort.
     * 
     * Set the playlist located at the corresponding playlistIndex
     * in decreasing popularity index order.
     * 
     * This method should use a sort that has O(nlogn), such as with merge sort.
     * 
     * @param playlistIndex the playlist to shuffle
     */
    public void sortPlaylist(int playlistIndex) {

        // WRITE YOUR CODE HERE

    }

    /**
     * ****DO NOT**** UPDATE THIS METHOD
     * Plays playlist by index; can use this method to debug.
     * 
     * @param playlistIndex the playlist to print
     * @param repeats       number of times to repeat playlist
     * @throws InterruptedException
     */
    public void playPlaylist(int playlistIndex, int repeats) {
        /* DO NOT UPDATE THIS METHOD */

        final String NO_SONG_MSG = " has no link to a song! Playing next...";
        if (songLibrary.get(playlistIndex).getLast() == null) {
            StdOut.println("Nothing to play.");
            return;
        }

        SongNode ptr = songLibrary.get(playlistIndex).getLast().getNext(), first = ptr;

        do {
            StdOut.print("\r" + ptr.getSong().toString());
            if (ptr.getSong().getLink() != null) {
                StdAudio.play(ptr.getSong().getLink());
                for (int ii = 0; ii < ptr.getSong().toString().length(); ii++)
                    StdOut.print("\b \b");
            } else {
                StdOut.print(NO_SONG_MSG);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                for (int ii = 0; ii < NO_SONG_MSG.length(); ii++)
                    StdOut.print("\b \b");
            }

            ptr = ptr.getNext();
            if (ptr == first)
                repeats--;
        } while (ptr != first || repeats > 0);
    }

    /**
     * ****DO NOT**** UPDATE THIS METHOD
     * Prints playlist by index; can use this method to debug.
     * 
     * @param playlistIndex the playlist to print
     */
    public void printPlaylist(int playlistIndex) {
        StdOut.printf("%nPlaylist at index %d (%d song(s)):%n", playlistIndex,
                songLibrary.get(playlistIndex).getSize());
        if (songLibrary.get(playlistIndex).getLast() == null) {
            StdOut.println("EMPTY");
            return;
        }
        SongNode ptr;
        for (ptr = songLibrary.get(playlistIndex).getLast().getNext(); ptr != songLibrary.get(playlistIndex)
                .getLast(); ptr = ptr.getNext()) {
            StdOut.print(ptr.getSong().toString() + " -> ");
        }
        if (ptr == songLibrary.get(playlistIndex).getLast()) {
            StdOut.print(songLibrary.get(playlistIndex).getLast().getSong().toString() + " - POINTS TO FRONT");
        }
        StdOut.println();
    }

    public void printLibrary() {
        if (songLibrary.size() == 0) {
            StdOut.println("\nYour library is empty!");
        } else {
            for (int ii = 0; ii < songLibrary.size(); ii++) {
                printPlaylist(ii);
            }
        }
    }

    /*
     * Used to get and set objects.
     * DO NOT edit.
     */
    public ArrayList<Playlist> getPlaylists() {
        return songLibrary;
    }

    public void setPlaylists(ArrayList<Playlist> p) {
        songLibrary = p;
    }
}
