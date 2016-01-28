import java.util.Iterator;
import java.util.ArrayList;
import java.util.Random;

/**
 * A class to hold details of audio tracks.
 * Individual tracks may be played.
 * 
 * @author David J. Barnes and Michael KÃ¶lling
 * @version 2011.07.31
 */
public class MusicOrganizer
{
    // An ArrayList for storing music tracks.
    private ArrayList<Track> tracks;
    // A player for the music tracks.
    private MusicPlayer player;
    // A reader that can read music files and load them as tracks.
    private TrackReader reader;
    // Informa si hay un track reproduciendose en ese momento (true == hay track reproduciendose, false == no hay reproducción en curso)
    private boolean isPlayingNow;
    // Objeto Iterator que recorre todas las canciones del organizador
    private Iterator<Track> iterador;

    /**
     * Create a MusicOrganizer
     */
    public MusicOrganizer(String carpetaContenedora)
    {
        tracks = new ArrayList<Track>();
        player = new MusicPlayer();
        reader = new TrackReader();
        readLibrary(carpetaContenedora);
        System.out.println("Music library loaded. " + getNumberOfTracks() + " tracks.");
        System.out.println();
        isPlayingNow = false;
        iterador = tracks.iterator();
    }

    /**
     * Add a track file to the collection.
     * @param filename The file name of the track to be added.
     */
    public void addFile(String filename)
    {
        tracks.add(new Track(filename));
    }

    /**
     * Add a track to the collection.
     * @param track The track to be added.
     */
    public void addTrack(Track track)
    {
        tracks.add(track);
    }

    /**
     * Play a track in the collection.
     * @param index The index of the track to be played.
     */
    public void playTrack(int index)
    {
        if (!isPlayingNow) {
            if(indexValid(index)) {
                Track track = tracks.get(index);
                player.startPlaying(track.getFilename());
                System.out.println("Now playing: " + track.getArtist() + " - " + track.getTitle());
                track.playCountIncrement();
                isPlayingNow = true;
            }
        }
        else {
            System.out.println("[ERROR] Ya hay una canción reproduciendose");
        }
    }

    /**
     * Return the number of tracks in the collection.
     * @return The number of tracks in the collection.
     */
    public int getNumberOfTracks()
    {
        return tracks.size();
    }

    /**
     * List a track from the collection.
     * @param index The index of the track to be listed.
     */
    public void listTrack(int index)
    {
        System.out.print("Track " + index + ": ");
        Track track = tracks.get(index);
        System.out.println(track.getDetails());
    }

    /**
     * Show a list of all the tracks in the collection.
     */
    public void listAllTracks()
    {
        System.out.println("Track listing: ");

        for(Track track : tracks) {
            System.out.println(track.getDetails());
        }
        System.out.println();
    }

    /**
     * List all tracks by the given artist.
     * @param artist The artist's name.
     */
    public void listByArtist(String artist)
    {
        for(Track track : tracks) {
            if(track.getArtist().contains(artist)) {
                System.out.println(track.getDetails());
            }
        }
    }

    /**
     * Remove a track from the collection.
     * @param index The index of the track to be removed.
     */
    public void removeTrack(int index)
    {
        if(indexValid(index)) {
            tracks.remove(index);
        }
    }

    /**
     * Play the first track in the collection, if there is one.
     */
    public void playFirst()
    {
        if (!isPlayingNow) {
            if(tracks.size() > 0) {
                player.startPlaying(tracks.get(0).getFilename());
                tracks.get(0).playCountIncrement();
                isPlayingNow = true;
            }
        }
        else {
            System.out.println("[ERROR] Ya hay una canción reproduciendose");
        }
    }

    /**
     * Stop the player.
     */
    public void stopPlaying()
    {
        player.stop();
        isPlayingNow = false;
    }

    /**
     * Determine whether the given index is valid for the collection.
     * Print an error message if it is not.
     * @param index The index to be checked.
     * @return true if the index is valid, false otherwise.
     */
    private boolean indexValid(int index)
    {
        // The return value.
        // Set according to whether the index is valid or not.
        boolean valid;

        if(index < 0) {
            System.out.println("Index cannot be negative: " + index);
            valid = false;
        }
        else if(index >= tracks.size()) {
            System.out.println("Index is too large: " + index);
            valid = false;
        }
        else {
            valid = true;
        }
        return valid;
    }

    private void readLibrary(String folderName)
    {
        ArrayList<Track> tempTracks = reader.readTracks(folderName, ".mp3");

        // Put all thetracks into the organizer.
        for(Track track : tempTracks) {
            addTrack(track);
        }
    }

    /**
     * Método que toma un parámetro de tipo String y muestra por pantalla la información
     * de los tracks que contienen dicha cadena en el título dela canción
     */
    public void findInTitle(String palabraBuscada)
    {
        for (Track trackEnLista : tracks) {
            if (trackEnLista.getTitle().contains(palabraBuscada)) {
                System.out.println(trackEnLista.getDetails());
            }
        }
    }

    /**
     * Método para modificar el atributo style en una canción
     */
    public void changeStyle(String style,int index)
    {
        tracks.get(index).setStyle(style);
    }

    /**
     * Método que informa por pantala si en ese momento está reproduciendo un track
     */
    public void isPlaying()
    {
        if (isPlayingNow) {
            System.out.println("Hay una reproducción en curso");
        }
        else {
            System.out.println("No hay ninguna reproducción en curso");
        }
    }
    
    /**
     * Método que muestra los detalles de todos los tracks almacenados 
     * en un organizador usando iterator
     */
    public void listAllTrackWithIterator()
    {
        while (iterador.hasNext()) {
            System.out.println(iterador.next().getDetails());
        }     
    }
    
    /**
     * Método que permite eliminar del organizador los tracks que 
     * contengan un determinado artista
     */
    public void removeByArtist(String artista)
    {
        while (iterador.hasNext()) {
            if (iterador.next().getArtist().contains(artista)) {
                iterador.remove();
            }
        }
    }
    
    /**
     * Método que permite eliminar del organizador los tracks que
     * contengan un determinado titulo
     */
    public void removeByTitle(String titulo)
    {
        while (iterador.hasNext()) {
            if (iterador.next().getTitle().contains(titulo)) {
                iterador.remove();
            } 
        }
    }
    
    /**
     * Método que reproduce una canción al azar de la lista
     */
    public void playRandom()
    {
        Random random = new Random();
        int numeroRandom = random.nextInt(tracks.size());
        playTrack(numeroRandom);
    }
}
