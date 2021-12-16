package com.Levels;

import com.Exceptions.LoadLevelException;

public interface ILevel {

    void loadLevel(String path) throws LoadLevelException;
}
