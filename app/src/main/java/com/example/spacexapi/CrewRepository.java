package com.example.spacexapi;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class CrewRepository {

    private CrewDao crewDao;

    public CrewRepository(Context context) {
        CrewDatabase crewDatabase = CrewDatabase.buildInstance(context);
        crewDao = crewDatabase.crewDao();
    }

    public void Insert(CrewModal crewModal) {
        new InsertAsync(crewDao).execute(crewModal);
    }

    public List<CrewModal> SelectAll() throws ExecutionException, InterruptedException {
        return new SelectAsync(crewDao).execute().get();
    }

    public void DeleteAll() {
        new DeleteAsync(crewDao).execute();
    }

}

class InsertAsync extends AsyncTask<CrewModal, Void, Void> {
    private CrewDao crewDao;

    public InsertAsync(CrewDao crewDao) {
        this.crewDao = crewDao;
    }

    @Override
    protected Void doInBackground(CrewModal... crewModals) {

        crewDao.insert(crewModals[0]);
        return null;
    }
}

class DeleteAsync extends AsyncTask<Void, Void, Void> {
    private CrewDao crewDao;

    public DeleteAsync(CrewDao crewDao) {
        this.crewDao = crewDao;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        crewDao.deleteAll();
        return null;
    }
}

class SelectAsync extends AsyncTask<Void, Void, List<CrewModal>> {
    private CrewDao crewDao;

    public SelectAsync(CrewDao crewDao) {
        this.crewDao = crewDao;
    }

    @Override
    protected List<CrewModal> doInBackground(Void... voids) {

        return crewDao.selectAll();
    }
}
