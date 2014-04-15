package com.revItUp.musicPlayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Random;
import android.annotation.SuppressLint;
import android.app.Activity;


public class PlaylistMaker extends Activity {
	// list of intervals
	private ArrayList<Integer> intervalList;

	// list of songs
	private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();

	// ascending order
	private ArrayList<ArrayList<Integer>> songSort;

	// playlist
	private ArrayList<Integer> playList;

	private int N;
	private double dN;
	private Random rand;

	public PlaylistMaker(ArrayList<Integer> tArray,ArrayList<HashMap<String, String>> songsLists) {
		intervalList = tArray;
		rand = new Random();
		playList = new ArrayList<Integer>();
		songsList = songsLists;
		N = songsList.size();
		dN = (double) N;
		sortSongs();
		makePlayList();
		System.out.println("Playlist for this workout: " + playList.toString());
	}

	public ArrayList<Integer> getPlayList() {
		if (playList.size() > 0) {
			return playList;
		}
		return null;
	}

	private void makePlayList() {
		int restIntensity = 2;

		int intervalN = intervalList.size() / 3;
		System.out.println("intervalN:" + intervalN);
		int intervalTime = 0;
		int intensity = 0;
		int restTime = 0;
		for (int i = 0; i < intervalN; i++) {
			intervalTime = intervalList.get(3 * i);
			intensity = intervalList.get(3 * i + 1);
			restTime = intervalList.get(3 * i + 2);
			intervalTime *= 60*1000;
			restTime *= 60*1000;
			System.out.println("Interval Time:" + intervalTime + " Intensity: " + intensity + " RestTime " + restTime );
			// add workout songs
			int intervalPlaylistLength = 0;
			while (intervalPlaylistLength < intervalTime) {
				int newSong = songSort.get(getSong(intensity)).get(0);
				System.out.println("newsong:" + newSong);
				playList.add(newSong);
				System.out.println("temptime:" + intervalPlaylistLength);
				intervalPlaylistLength += (int) Double.parseDouble(songsList.get(newSong)
						.get("duration"));
				System.out.println("temptime:" + intervalPlaylistLength);

			}

			// add rest songs
			intervalPlaylistLength = 0;
			while (intervalPlaylistLength < restTime) {
				int newSong = songSort.get(getSong(restIntensity)).get(0);
				System.out.println("newSong: " + newSong);
				playList.add(newSong);
				intervalPlaylistLength += (int) Double.parseDouble(songsList.get(newSong)
						.get("duration"));
				System.out.println("temptime:" + intervalPlaylistLength);
			}
		}
	}

	private int getSong(int intensity) {
		double dIntensity = ((double) intensity) / 10.0;
		double dUpper = dIntensity + 0.2;
		double dLower = dIntensity - 0.2;
		int iUpper = (int) (dUpper * dN);
		int iLower = (int) (dLower * dN);
		System.out.println("ILower: " + iLower+" IUpper: " + iUpper);
		if (iUpper > N - 1)
			iUpper = N - 1;
		if (iLower < 0)
			iLower = 0;
		return rand.nextInt(iUpper - iLower + 1) + iLower;
	}

	// sort the songs by bpm / w/e formula
	private void sortSongs() {
		System.out.println("N:" + N);
		songSort = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < N; i++) {
			ArrayList<Integer> sng = new ArrayList<Integer>();
			sng.add(i);
			sng.add(intensityOf(i));
			songSort.add(sng);
			System.out.println(songsList.get(i).get("songTitle") + ": "
					+intensityOf(i));
		}
		Comparator<ArrayList<Integer>> comp = new Comparator<ArrayList<Integer>>() {
		    @SuppressLint("NewApi")
			@Override
		    public int compare(ArrayList<Integer> o1, ArrayList<Integer> o2) {
		        return Integer.compare(o1.get(1),o2.get(1));
		    }
		}; 
		Collections.sort(songSort,comp);
		System.out.println("Song Sort: " + songSort);
	}
	

	// get intensity of song
	private int intensityOf(int index) {
		// currently only using tempo, should try other things
		return (int) Double.parseDouble(songsList.get(index).get("tempo"));
	}
}
