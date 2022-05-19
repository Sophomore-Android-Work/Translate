package service;

import pojo.Favorite;

import java.util.List;

public interface FavoriteService {

    //查询所有收藏记录
    public List<Favorite> getAllFavorite(int id);

}
