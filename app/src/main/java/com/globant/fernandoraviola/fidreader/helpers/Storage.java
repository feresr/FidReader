package com.globant.fernandoraviola.fidreader.helpers;

import android.content.Context;

import com.globant.fernandoraviola.fidreader.models.Entry;
import com.globant.fernandoraviola.fidreader.models.Favorite;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * This class abstracts Realm interactions and servers as a entry point to db persistence
 */
public class Storage {

    private static Storage instance;
    private Realm realm;

    private Storage(Context context) {
        this.realm = Realm.getInstance(context);
    }

    public static Storage getInstance(Context context) {
        if (instance == null) {
            instance = new Storage(context);
        }
        return instance;
    }

    public void saveFavorite(Entry entry) {
        realm.beginTransaction();
        Favorite favorite = realm.createObject(Favorite.class);
        favorite.setTitle(entry.getTitle());
        favorite.setContent(entry.getContent());
        favorite.setPublishedDate(entry.getPublishedDate());
        favorite.setLink(entry.getLink());
        favorite.setAuthor(entry.getAuthor());
        realm.commitTransaction();
    }

    public void removeFromFavorites(Entry entry) {
        realm.beginTransaction();
        //Remove entry from favorites.
        Favorite favorite = realm.where(Favorite.class)
                .equalTo("title", entry.getTitle())
                .findFirst();
        if (favorite != null) {
            favorite.removeFromRealm();
        }
        realm.commitTransaction();
    }

    /**
     * Check if a particular entry exists in the db.
     *
     * @param entry Entry
     * @return whether a particular entry already exists inside the db or not.
     */
    public boolean isFavorite(Entry entry) {
        return realm.where(Favorite.class).equalTo("title", entry.getTitle()).findFirst() != null;
    }

    public RealmResults<Favorite> getAllFavorites() {
        return realm.where(Favorite.class).findAll();
    }

}
