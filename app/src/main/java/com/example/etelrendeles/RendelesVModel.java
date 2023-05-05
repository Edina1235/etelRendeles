package com.example.etelrendeles;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.Objects;

public class RendelesVModel extends AndroidViewModel {
    private RendelesRepository repository;
    private LiveData<List<Rendeles>> rendelesek;

    public RendelesVModel(@NonNull Application application) {
        super(application);
        this.repository = new RendelesRepository(application);
        this.rendelesek = repository.getAll();
    }

    public LiveData<List<Rendeles>> getAll() {
        if(this.rendelesek==null) {
            this.rendelesek = this.repository.getAll();
        }
        return this.rendelesek;
    }

    public List<Rendeles> getAllList() {
        return this.repository.getAllList();
    }

    public int osszeg(String rendelo) {
        return this.repository.osszeg(rendelo);
    }

    public void delete(Rendeles rendeles) {
        this.repository.delete(rendeles);
    }

    public void insert(Rendeles rendeles) {
        this.repository.add(rendeles);
        Log.d(EtelekActivity.class.getName(),"Elmentve(vmodel):" + rendeles.getEtelNev());
    }

    public void update(Rendeles rendeles) {
        this.repository.update(rendeles);
    }

    public LiveData<List<Rendeles>> getByRendelo(String rendelo) {
        if(this.rendelesek.getValue()!=null) {
            Log.d(EtelekActivity.class.getName(), this.rendelesek.getValue().get(0).getEtelNev());
        }
        return repository.getByRendelo(rendelo);
    }

    public void deleteAllByRendelo(String rendelo) {
        this.repository.deleteAllByRendelo(rendelo);
    }
}
