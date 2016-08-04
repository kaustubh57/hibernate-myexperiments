package com.hibernate.myexperiments.resource;

import com.hibernate.myexperiments.dao.StoryboardDao;
import com.hibernate.myexperiments.models.Storyboard;

import java.util.List;

/**
 * Created by kaustubh on 7/22/16.
 */
public class StoryboardResource {

    public static void testStoryboardData() {
        System.out.println(" =======READ =======");

//        List<Storyboard> storyboards = StoryboardDao.read();
//        for(Storyboard e: storyboards) {
//            System.out.println(e.toString());
//        }

//        Storyboard storyboard = StoryboardDao.readStoryboardById(68950L);

        List<Storyboard> storyboards = StoryboardDao.readStoryboardByCriteria();
//        List<Storyboard> storyboards = StoryboardDao.readStoryboardByCriteriaFormula();
        for (Storyboard e : storyboards) {
            System.out.println(e.toString());
        }
    }
}
