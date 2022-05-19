package service;

import mapper.FavoriteMapper;
import pojo.Favorite;

import java.util.List;

public class FavoriteServiceImpl implements FavoriteService{
    private FavoriteMapper favoriteMapper;

    public void setFavoriteMapper(FavoriteMapper favoriteMapper) {
        this.favoriteMapper = favoriteMapper;
    }

    @Override
    public List<Favorite> getAllFavorite(int id) {
        return favoriteMapper.getAllFavorite(id);
    }


}
