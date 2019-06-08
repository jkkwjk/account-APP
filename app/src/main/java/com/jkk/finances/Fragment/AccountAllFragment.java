package com.jkk.finances.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jkk.finances.Model.AccountInfo;
import com.jkk.finances.R;
import com.jkk.finances.Utils.StampDate;

import java.util.ArrayList;
import java.util.List;

public class AccountAllFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private AccountAdapter accountAdapter;
    private ArrayList<AccountInfo> list;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_all,container,false);
        mRecyclerView = view.findViewById(R.id.view_recycle_account_all);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();
        return view;
    }

    private void updateUI(){
        Bundle bundle = this.getArguments();
        if (bundle != null){
            list = (ArrayList<AccountInfo>)bundle.getSerializable("account");
        }
        accountAdapter = new AccountAdapter(list);
        mRecyclerView.setAdapter(accountAdapter);
    }
    private class AccountHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView textView_textView_account_add_time;
        private TextView textView_textView_account_type;
        private TextView textView_textView_account_money;
        private TextView textView_textView_account_used;
        public AccountHolder(@NonNull View itemView) {
            super(itemView);
            textView_textView_account_add_time = itemView.findViewById(R.id.textView_account_add_time);
            textView_textView_account_money = itemView.findViewById(R.id.textView_account_money);
            textView_textView_account_type = itemView.findViewById(R.id.textView_account_type);
            textView_textView_account_used = itemView.findViewById(R.id.textView_account_used);

            itemView.setOnClickListener(this);
        }
        public void bind(AccountInfo accountInfo){
            textView_textView_account_used.setText(accountInfo.getUseName());
            textView_textView_account_type.setText(accountInfo.getType());
            textView_textView_account_money.setText(accountInfo.getMoney().toPlainString());
            textView_textView_account_add_time.setText(StampDate.stampToDate(accountInfo.getTime()));
        }
        @Override
        public void onClick(View v) {

        }
    }

    private class AccountAdapter extends RecyclerView.Adapter<AccountHolder>{
        private List<AccountInfo> accountInfos;
        @NonNull
        @Override
        public AccountHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.fragment_account_all_item,null);

            return new AccountHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull AccountHolder accountHolder, int i) {
            AccountInfo accountInfo = accountInfos.get(i);

            accountHolder.bind(accountInfo);
        }

        @Override
        public int getItemCount() {
            return accountInfos.size();
        }
        public AccountAdapter(List<AccountInfo> accountInfos){this.accountInfos = accountInfos;}
    }
}
