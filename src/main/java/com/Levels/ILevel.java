package com.Levels;

import com.Exceptions.LoadLevelException;

public interface ILevel {

    public void loadLevel(String path) throws LoadLevelException;
}
