package com.revItUp.musicPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.Random;

import com.revItUp.musicPlayer.R;
import com.revItUp.musicPlayer.SongsManager.GetSongList;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PlaylistMaker {
	//list of intervals
	private ArrayList<Integer> intervalList;
	
	//list of songs
	private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
	
	//ascending order
	private int[][] songSort;
	
	//playlist
	private ArrayList<Integer> playList;
	
	
	private int N;
	private double dN;
	private Random rand;
	
	public PlaylistMaker(ArrayList<Integer> tArray)
	{
		intervalList = tArray;
		rand = new Random();
		playList = new ArrayList<Integer>();
		initSongs();
		sortSongs();
		makePlayList();
	}
	
	public ArrayList<Integer> getPlayList()
	{
		if(playList.size()>0)
		{
			return playList;
		}
		return null;
	}
	
	private void makePlayList(){
		int restIntensity = 2;
		
		int intervalN = intervalList.size()/3;
		System.out.println("intervalN:" + intervalN);
		int intervalTime = 0;
		int intensity = 0;
		int restTime = 0;
		for(int i=0;i<intervalN;i++)
		{
			intervalTime = intervalList.get(3*i);
			intensity = intervalList.get(3*i+1);
			restTime = intervalList.get(3*i+2);
			intervalTime *= 60;
			restTime *= 60;
			System.out.println(intervalTime + " " + intensity +" " + restTime);
			//add workout songs
			int tempTime = 0;
			while(tempTime < intervalTime)
			{
				int newSong = getSong(intensity);
				System.out.println("newsong:" + newSong);
				playList.add(newSong);
				System.out.println("temptime:"+tempTime);
				tempTime += (int)Double.parseDouble(songsList.get(newSong).get("tempo"));
			}
			
			//add rest songs
			tempTime = 0;
			while(tempTime < restTime)
			{
				int newSong = getSong(restIntensity);
				playList.add(newSong);
				tempTime += (int)Double.parseDouble(songsList.get(newSong).get("tempo"));
			}
			
		}
	}
	
	private int getSong(int intensity)
	{
		double dIntensity = ((double)intensity)/10.0;
		double dUpper = dIntensity + 0.2;
		double dLower = dIntensity - 0.2;
		int iUpper = (int) (dUpper*dN);
		int iLower = (int) (dLower*dN);
		
		if(iUpper > N-1) iUpper = N-1;
		if(iLower < 0) iLower = 0;
		return rand.nextInt(iUpper-iLower+1) + iLower;
		
	}
	//sort the songs by bpm / w/e formula
	private void sortSongs()
	{
		
		System.out.println("N:" + N);
		songSort = new int[N + 1][];
		for(int i=0;i<N;i++)
		{
			songSort[i] = new int[2];
			songSort[i][0] = i;
			songSort[i][1] = intensityOf(i); 
			System.out.println(songsList.get(i).get("songTitle") + ": " + songSort[i][1]);
		}
		int tempInt1 =0, tempInt2=0;
		for(int i=0;i<N-1;i++)
		{
			for(int j=i;j<N-1;j++)
			{
				if(songSort[j][1]>songSort[j+1][1])
				{
					tempInt1 = songSort[j][1];
					songSort[j][1] = songSort[j+1][1];
					songSort[j+1][1] = tempInt1;
					
					tempInt2 = songSort[j][0];
					songSort[j][0] = songSort[j+1][0];
					songSort[j+1][0] = tempInt2;
					
				}
			}
		}
	}
	
	//get intensity of song
	private int intensityOf(int index)
	{
		//tempo
		return (int)Double.parseDouble(songsList.get(index).get("tempo"));
	}
	
	//get the song data
	private void initSongs()
	{
		//ArrayList<HashMap<String, String>> songsListData = new ArrayList<HashMap<String, String>>();

		SongsManager plm = new SongsManager();
		// get all songs from sdcard
		GetSongList getPL =  plm.new GetSongList();
		getPL.execute();
		try {
			this. songsList = getPL.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		N = songsList.size();
		dN = (double)N;
	}
}
