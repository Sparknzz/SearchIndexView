package com.nypzxy.searchforindex;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactAdapter extends BaseAdapter {
    private final ArrayList<Contact> contacts;

    public ContactAdapter(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Contact getItem(int position) {
        return contacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Contact contact = getItem(position);

        //获取联系人的alphabet第一个字母
        String letter = contact.alphabet.charAt(0) + "";
        //判断前一个contact对象中的首字母是否相同  如果相同 则将这个view的tvTitle去掉
        if (position > 0) {
            Contact preContact = getItem(position - 1);
            if (TextUtils.equals(letter, preContact.alphabet.charAt(0) + "")) {
                //说明前后两个首字母相同  所以 将holder.title隐藏
                holder.tvLetter.setVisibility(View.GONE);
                holder.tvLetter.setText(letter);
            } else {
                //如果不一样，就显示
                holder.tvLetter.setVisibility(View.VISIBLE);
                holder.tvLetter.setText(letter);
            }
        } else {
            //第0条数据全部显示
//            holder.tvLetter.setVisibility(View.VISIBLE);
            holder.tvLetter.setText(letter);
        }

        holder.tvName.setText(contact.name);


        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_letter)
        TextView tvLetter;
        @BindView(R.id.tv_name)
        TextView tvName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
