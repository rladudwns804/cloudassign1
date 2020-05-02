/*
 * 
 * Copyright 2014 Jules White
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package org.magnum.dataup;
import org.magnum.dataup.VideoSvcApi;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.magnum.dataup.model.Video;
import org.magnum.dataup.model.VideoStatus;
import org.magnum.dataup.model.VideoStatus.VideoState;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.mime.TypedFile;

@RestController
public class VideoController {
	private static final AtomicLong currentId = new AtomicLong(0L);
	private Map<Long,Video> videos = new HashMap<Long, Video>();
	
	
	
	@RequestMapping(value = "/video", method = RequestMethod.POST, consumes = "application/json")
	public Video addVideo(@RequestBody Video video) throws IOException {
		save(video);
		return video;
	}
	
	@RequestMapping(value = "/video/{id}/data", method = RequestMethod.POST)
	public VideoStatus getStatus(@PathVariable long id, @RequestParam MultipartFile data) throws IOException {
		VideoFileManager.get().saveSomeVideo(videos.get(id),data);
		VideoStatus state= new VideoStatus(VideoState.READY);
		return state;
	}
	
	
	@RequestMapping(value = "/video/{id}/data", method = RequestMethod.GET)
	public Video getVideo(@PathVariable long id, @RequestParam MultipartFile data) {
		
		
		return null;
	}

	public Video save(Video entity) {
		checkAndSetId(entity);
		videos.put(entity.getId(), entity);
		return entity;
	}

	private void checkAndSetId(Video entity) {
		if(entity.getId() == 0){
			entity.setId(currentId.incrementAndGet());
		}
	}
	
	public Video getVideo(long id) {
		return videos.get(id);
		
	}
	
	
	 
/*	@Override
	public Collection<Video> getVideoList() {
		Gson json = new Gson();
		for(Video v :Collection<Video>){
		String response= json.toJson(v.toString());
		System.out.println(response);
		}
		return null;
	}
	@Override
	public Video addVideo(Video v) {
		
		return null;
	}
	@RequestMapping(value = VideoSvcApi.VIDEO_DATA_PATH,
					method = RequestMethod.POST)
	@ResponseBody
	@Override
	public VideoStatus setVideoData(long id, TypedFile videoData) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Response getData(long id) {
		// TODO Auto-generated method stub
		return null;
	}
	 
*/	 
		/**
	 * You will need to create one or more Spring controllers to fulfill the
	 * requirements of the assignment. If you use this file, please rename it
	 * to something other than "AnEmptyController"
	 * 
	 * 
		 ________  ________  ________  ________          ___       ___  ___  ________  ___  __       
		|\   ____\|\   __  \|\   __  \|\   ___ \        |\  \     |\  \|\  \|\   ____\|\  \|\  \     
		\ \  \___|\ \  \|\  \ \  \|\  \ \  \_|\ \       \ \  \    \ \  \\\  \ \  \___|\ \  \/  /|_   
		 \ \  \  __\ \  \\\  \ \  \\\  \ \  \ \\ \       \ \  \    \ \  \\\  \ \  \    \ \   ___  \  
		  \ \  \|\  \ \  \\\  \ \  \\\  \ \  \_\\ \       \ \  \____\ \  \\\  \ \  \____\ \  \\ \  \ 
		   \ \_______\ \_______\ \_______\ \_______\       \ \_______\ \_______\ \_______\ \__\\ \__\
		    \|_______|\|_______|\|_______|\|_______|        \|_______|\|_______|\|_______|\|__| \|__|
                                                                                                                                                                                                                                                                        
	 * 
	 */
	
}
