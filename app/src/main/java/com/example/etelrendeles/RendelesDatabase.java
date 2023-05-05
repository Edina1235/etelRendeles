package com.example.etelrendeles;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.List;

@Database(entities = {Rendeles.class}, version = 2, exportSchema = true)
public abstract class RendelesDatabase extends RoomDatabase {
    public abstract RendelesDAO rendelesDAO();
    private static RendelesDatabase instance;

    public static RendelesDatabase getInstance(Context context) {
        if(instance == null) {
            synchronized (RendelesDatabase.class) {
                if(instance == null) {
                    instance = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    RendelesDatabase.class, "Rendeles")
                            .fallbackToDestructiveMigration()
                            .addCallback(callback).build();
                }
            }
        }
        return instance;
    }

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback() {
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            new InitDb(instance).execute();
        }
    };

    private static class InitDb extends AsyncTask<Void, Void, Void> {
        private RendelesDAO rDao;

        InitDb(RendelesDatabase db) {
            this.rDao = db.rendelesDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(rDao.getAll().getValue()!=null) {
                LiveData<List<Rendeles>> rendelesek = rDao.getAll();
                for(int i=0;i<rendelesek.getValue().size();i++) {
                    this.rDao.add(rendelesek.getValue().get(i));
                }
            }
            Log.d(EtelekActivity.class.getName(),"Elmentve(database):");

            return null;
        }
    }

}
