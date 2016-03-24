package hu.reddit.developer.data;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

/**
 * Created by László Gálosi on 23/03/16
 */
public class EntityJsonMapperFactory {

    private static Gson sGson = new Gson();
    private static JsonParser sJsonParser = new JsonParser();

    public static EntityJsonMapperFactory getInstance() {
        return SInstanceHolder.sInstance;
    }

    public <E extends BasicEntity> EntityJsonMapper create(Class<E> entityClass) {
        if (entityClass == RedditRootEntity.class) {
            return SRedditListingMapperInstanceHolder.sInstanceHolder;
        } else if (entityClass == RedditListingDataEntity.class) {
            return SRedditListingDataMapperInstanceHolder.sInstanceHolder;
        } else if (entityClass == RedditDataEntity.class) {
            return SRedditDataEntityMapperInstanceHolder.sInstanceHolder;
        } else if (entityClass == RedditEntity.class) {
            return SRedditEntityMapperInstanceHolder.sInstanceHolder;
        } else if (entityClass == PreviewEntity.class) {
            return SPreviewEntityMapperInstanceHolder.sInstanceHolder;
        } else if (entityClass == ImageDataEntity.class) {
            return SImageDataEntityMapperInstanceHolder.sInstanceHolder;
        } else if (entityClass == ImageEntity.class) {
            return SImageEntityMapperInstanceHolder.sInstanceHolder;
        } else {
            throw new IllegalArgumentException(
                  "Invalid domain and entityClass class for mapping between "
                        + entityClass.getSimpleName());
        }
    }

    private static class SInstanceHolder {
        private static final EntityJsonMapperFactory sInstance = new EntityJsonMapperFactory();
    }

    private static class SRedditListingMapperInstanceHolder {
        private static final EntityJsonMapper<RedditRootEntity> sInstanceHolder =
              new EntityJsonMapper<>(sGson, sJsonParser);
    }

    private static class SRedditDataEntityMapperInstanceHolder {
        private static final EntityJsonMapper<RedditListingDataEntity> sInstanceHolder =
              new EntityJsonMapper<>(sGson, sJsonParser);
    }

    private static class SRedditListingDataMapperInstanceHolder {
        private static final EntityJsonMapper<RedditListingDataEntity> sInstanceHolder =
              new EntityJsonMapper<>(sGson, sJsonParser);
    }

    private static class SRedditEntityMapperInstanceHolder {
        private static final EntityJsonMapper<RedditEntity> sInstanceHolder =
              new EntityJsonMapper<>(sGson, sJsonParser);
    }

    private static class SPreviewEntityMapperInstanceHolder {
        private static final EntityJsonMapper<PreviewEntity> sInstanceHolder =
              new EntityJsonMapper<>(sGson, sJsonParser);
    }

    private static class SImageDataEntityMapperInstanceHolder {
        private static final EntityJsonMapper<ImageDataEntity> sInstanceHolder =
              new EntityJsonMapper<>(sGson, sJsonParser);
    }

    private static class SImageEntityMapperInstanceHolder {
        private static final EntityJsonMapper<ImageEntity> sInstanceHolder =
              new EntityJsonMapper<>(sGson, sJsonParser);
    }

    private EntityJsonMapperFactory() {
    }
}
