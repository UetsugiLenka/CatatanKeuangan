package com.azhar.hitungpengeluaran.view.fragment.saldo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.azhar.hitungpengeluaran.R;
import com.azhar.hitungpengeluaran.model.ModelDatabase;
import com.azhar.hitungpengeluaran.utils.FunctionHelper;
import com.azhar.hitungpengeluaran.view.fragment.pengeluaran.pemasukan.add.AddPemasukanActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class SaldoFragment extends Fragment implements SaldoAdapter.PemasukanAdapterCallback {

    private SaldoAdapter saldoAdapter;
    private SaldoViewModel saldoViewModel;
    private List<ModelDatabase> modelDatabaseList = new ArrayList<>();
    TextView tvTotal, tvNotFound;
    Button btnHapus;


    public SaldoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_saldo, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvTotal = view.findViewById(R.id.tvTotal);
        tvNotFound = view.findViewById(R.id.tvNotFound);
        btnHapus = view.findViewById(R.id.btnHapus);

        tvNotFound.setVisibility(View.GONE);

        initAdapter();
        observeData();
    }

    private void initAdapter() {
        saldoAdapter = new SaldoAdapter(requireContext(), modelDatabaseList, this);

    }

    private void observeData() {
        saldoViewModel = ViewModelProviders.of(this).get(SaldoViewModel.class);
        saldoViewModel.getPemasukan().observe(requireActivity(),
                new Observer<List<ModelDatabase>>() {
                    @Override
                    public void onChanged(List<ModelDatabase> pemasukan) {
                        saldoAdapter.addData(pemasukan);
                    }
                });

        saldoViewModel.calculateSelisih().observe(requireActivity(),
                new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                        if (integer == null) {
                            int totalPrice = 0;
                            String initPrice = FunctionHelper.rupiahFormat(totalPrice);
                            tvTotal.setText(initPrice);
                        } else {
                            int totalPrice = integer;
                            String initPrice = FunctionHelper.rupiahFormat(totalPrice);
                            tvTotal.setText(initPrice);
                        }
                    }
                });
    }


    @Override
    public void onEdit(ModelDatabase modelDatabase) {

    }

    @Override
    public void onDelete(ModelDatabase modelDatabase) {

    }
}
