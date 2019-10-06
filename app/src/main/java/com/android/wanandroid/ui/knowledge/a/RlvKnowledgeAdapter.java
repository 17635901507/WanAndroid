package com.android.wanandroid.ui.knowledge.a;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.wanandroid.R;
import com.android.wanandroid.data.entity.know.Children;
import com.android.wanandroid.data.entity.know.Knowledge;
import com.kkk.mvp.utils.SystemFacade;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mr.Li：lkx
 * @description: I brandished my keyboard and book, vowing to write the world clearly.
 * @date : 2019/10/4 11:23
 */
public class RlvKnowledgeAdapter extends RecyclerView.Adapter<RlvKnowledgeAdapter.ViewHolder> {
    Context mContext;

    List<Knowledge<Children>> list = new ArrayList<>();
    private final LayoutInflater mInflater;

    public void setList(List<Knowledge<Children>> list) {
        if(this.list != null){
            this.list.clear();
            this.list.addAll(list);
            notifyDataSetChanged();
        }
    }

    public RlvKnowledgeAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_knowledge, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Knowledge<Children> childrenKnowledge = list.get(position);
        holder.tv_title.setText(childrenKnowledge.name);
        holder.tv_title.setTextColor(SystemFacade.randomColor());
        StringBuilder sb = new StringBuilder();
        int childPosition = 0;
        for (int i = 0; i < childrenKnowledge.children.size(); i++) {
            sb.append(childrenKnowledge.children.get(i).name+"  ");
            childPosition = i;
        }
        holder.tv_child.setText(sb);

        int finalChildPosition = childPosition;
        holder.itemView.setOnClickListener(v -> {
            if(onItemClick != null){
                onItemClick.setOnItemClick(position, finalChildPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title,tv_child;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.know_ldege_tv_title);
            tv_child = itemView.findViewById(R.id.know_ledge_tv_child);
        }
    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface  OnItemClick{
        void setOnItemClick(int i,int childPosition);
    }
}
