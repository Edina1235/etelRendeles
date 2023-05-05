package com.example.etelrendeles;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class RendelesRepository {
    private RendelesDAO dao;
    private LiveData<List<Rendeles>> rendelesek;

    RendelesRepository(Application application) {
        RendelesDatabase db = RendelesDatabase.getInstance(application);
        dao = db.rendelesDAO();
        rendelesek = dao.getAll();
    }

    public List<Rendeles> getAllList() {
        List<Rendeles> rendelesList = new ArrayList<>();
        new Read(this.dao, rendelesList).execute();
        return rendelesList;
    }

    public LiveData<List<Rendeles>> getAll() {
        return dao.getAll();
    }

    private static class Read extends AsyncTask<Void, Void, List<Rendeles>> {
        private RendelesDAO rDAO;
        private List<Rendeles> rendeles;

        Read(RendelesDAO dao, List<Rendeles> rendelesList) {
            this.rDAO = dao;
            this.rendeles=rendelesList;
        }

        @Override
        protected List<Rendeles> doInBackground(Void... voids) {
            return rDAO.getAllList();
        }

        @Override
        protected void onPostExecute(List<Rendeles> rendeles) {
            super.onPostExecute(rendeles);
            this.rendeles.addAll(rendeles);
        }
    }

    public int osszeg(String rendelo) {
        int osszeg = 0;
        try {
            return new Osszeg(this.dao, osszeg).execute(rendelo).get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static class Osszeg extends AsyncTask<String, Void, Integer> {
        private RendelesDAO rDAO;
        private int ossz;

        Osszeg(RendelesDAO dao, int osszeg) {
            this.rDAO = dao;
            this.ossz=osszeg;
        }

        @Override
        protected Integer doInBackground(String... rendelo) {
            ossz = this.rDAO.osszeg(rendelo[0]);
            return ossz;
        }

        @Override
        protected void onPostExecute(Integer ossz) {
            super.onPostExecute(ossz);
            Log.d(EtelekActivity.class.getName(),"OSSZEGpost(vmodel):" + ossz);
            this.ossz=ossz;
        }
    }

    public void add(Rendeles rendeles) {
         new Insert(this.dao).execute(rendeles);
    }

    private static class Insert extends AsyncTask<Rendeles, Void, Void> {
        private RendelesDAO rDAO;

        Insert(RendelesDAO dao) {
            this.rDAO = dao;
        }

        @Override
        protected Void doInBackground(Rendeles... rendelesek) {
            Log.d(EtelekActivity.class.getName(), "itt is: " + rendelesek[0].getEtelNev());
            rDAO.add(rendelesek[0]);
            Log.d(EtelekActivity.class.getName(), "Elmentve(repository):" + rendelesek[0].getRendelo());

           return null;
        }
    }

    public void update(Rendeles rendeles) {
        new Update(this.dao).execute(rendeles);
    }

    private static class Update extends AsyncTask<Rendeles, Void, Void> {
        private RendelesDAO rDAO;

        Update(RendelesDAO dao) {
            this.rDAO = dao;
        }

        @Override
        protected Void doInBackground(Rendeles... rendelesek) {
            rDAO.update(rendelesek[0].getRendelo(), rendelesek[0].getEtelNev(), rendelesek[0].getMennyiseg(), rendelesek[0].getAr());
            return null;
        }
    }

    public LiveData<List<Rendeles>> getByRendelo(String rendelo) {
        return dao.getByRendelo(rendelo);
    }

    public void delete(Rendeles rendeles) {
        new Delete(this.dao).execute(rendeles);
    }

    private static class Delete extends AsyncTask<Rendeles, Void, Void> {
        private RendelesDAO rDAO;

        Delete(RendelesDAO dao) {
            this.rDAO = dao;
        }

        @Override
        protected Void doInBackground(Rendeles... rendelesek) {
            rDAO.delete(rendelesek[0]);
            return null;
        }
    }

    public void deleteAllByRendelo(String rendelo) {
        new DeleteAll(this.dao).execute(rendelo);
    }

    private static class DeleteAll extends AsyncTask<String, Void, Void> {
        private RendelesDAO rDAO;

        DeleteAll(RendelesDAO dao) {
            this.rDAO = dao;
        }

        @Override
        protected Void doInBackground(String... rendelo) {
            rDAO.deleteAllByRendelo(rendelo[0]);
            return null;
        }
    }


}
