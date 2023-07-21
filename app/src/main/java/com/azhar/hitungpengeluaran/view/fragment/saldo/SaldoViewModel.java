package com.azhar.hitungpengeluaran.view.fragment.saldo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.azhar.hitungpengeluaran.database.DatabaseClient;
import com.azhar.hitungpengeluaran.database.dao.DatabaseDao;
import com.azhar.hitungpengeluaran.model.ModelDatabase;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SaldoViewModel extends AndroidViewModel {

    private LiveData<List<ModelDatabase>> mPemasukan, mPengeluarans;
    private DatabaseDao databaseDao;
    private LiveData<Integer> mTotalPrice, mTotalSaldo;

    public SaldoViewModel(@NonNull Application application) {
        super(application);

        databaseDao = DatabaseClient.getInstance(application).getAppDatabase().databaseDao();
        mPemasukan = databaseDao.getAllPemasukan();
        mPengeluarans = databaseDao.getAllPengeluaran();
        mTotalPrice = databaseDao.calculateSelisih();

    }

    public LiveData<List<ModelDatabase>> getPemasukan() {
        return mPemasukan;
    }

    public LiveData<Integer> calculateSelisih() {return mTotalPrice;
    }

    public String deleteSingleData(final int uid) {
        String sKeterangan;
        try {
            Completable.fromAction(() -> databaseDao.deleteSinglePemasukan(uid))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe();
            sKeterangan = "OK";
        } catch (Exception e) {
            sKeterangan = "NO";
        }
        return sKeterangan;
    }

}
