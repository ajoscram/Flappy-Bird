package com.ajoscram.flappy_bird.scenes;

import java.util.Stack;

public class SceneManager {

    private Stack<Scene> scenes;

    public SceneManager(){
        scenes = new Stack();
    }

    public void push(Scene scene){
        if(!scenes.empty())
            scenes.peek().stop();
        scenes.push(scene);
    }

    public void pop(){
        Scene popped = scenes.pop();
        popped.dispose();
        if(!scenes.empty())
            scenes.peek().resume();
    }

    public Scene peek(){
        return scenes.peek();
    }

    public void dispose(){
        for(Scene scene : scenes){
            scene.dispose();
        }
    }
}
