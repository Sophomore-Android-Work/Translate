package mapper;

import pojo.Favorite;

import java.util.List;

public interface FavoriteMapper {

    //查询所有收藏记录
    public List<Favorite> getAllFavorite(int id);

}
