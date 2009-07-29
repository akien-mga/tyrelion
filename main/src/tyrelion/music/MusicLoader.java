/**
 * 
 */
package tyrelion.music;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

/**
 * @author jahudi
 *
 */
public class MusicLoader {
	
	/**
	 * A lift of all detected categories (subdirectories of "music"). 
	 */
	private String[] categories;
	private HashMap<String, ArrayList<Music>> musicMap;

	public MusicLoader() {
		initCategories();
		initMusic();
	}
	
	/**
	 * Creates a list of categories for the game musics. The list is based on the directory structure
	 * of the "music" directory.
	 */
	public void initCategories() {
		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File file, String name) {
				return file.isDirectory();
			}
		};
		File root = new File("res/music");
		setCategories(root.list(filter));
	}
	
	/**
	 * Fills the "musicMap" with all the music files in the "music" directory ordered by categories.
	 */
	public void initMusic() {
		musicMap = new HashMap<String, ArrayList<Music>>();
		for (String elem : categories) {
			musicMap.put(elem, scanMusic(elem));
		}
	}
	
	
	/**
	 * @param category the name of the category to scan for music
	 * @return an ArrayList of Music objects of the specified category
	 */
	public ArrayList<Music> scanMusic(String category) {
		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File file, String name) {
				return name.endsWith(".ogg");
			}
		};
		File dir = new File("res/music/"+category);
		File[] files = dir.listFiles(filter);
		ArrayList<Music> musics = new ArrayList<Music>();
		for (File elem : files) {
			try {
				musics.add(new Music(elem.getAbsolutePath(), true));
			} catch (SlickException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return musics;
	}

	/**
	 * @param categories the categories to set
	 */
	public void setCategories(String[] categories) {
		this.categories = categories;
	}

	/**
	 * @return the categories
	 */
	public String[] getCategories() {
		return categories;
	}

	/**
	 * @return the musicMap
	 */
	public HashMap<String, ArrayList<Music>> getMusicMap() {
		return musicMap;
	}

	/**
	 * @param musicMap the musicMap to set
	 */
	public void setMusicMap(HashMap<String, ArrayList<Music>> musicMap) {
		this.musicMap = musicMap;
	}
	
}
