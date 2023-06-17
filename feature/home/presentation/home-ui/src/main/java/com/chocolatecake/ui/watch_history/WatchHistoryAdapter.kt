package com.chocolatecake.ui.watch_history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.chocolatecake.bases.BaseAdapter
import com.chocolatecake.ui.home.R
import com.chocolatecake.ui.home.databinding.WatchHistoryRecyclerViewCardBinding
import com.chocolatecake.ui.home.databinding.WatchHistoryRecyclerViewTitleBinding
import com.chocolatecake.viewmodel.common.listener.MediaListener
import com.chocolatecake.viewmodel.watch_history.state_managment.WatchHistoryOnEventListeners

class WatchHistoryAdapter(
    private var items: List<WatchHistoryRecyclerItem>,
    private val listener: MediaListener
) : BaseAdapter<WatchHistoryRecyclerItem>(items, listener) {
    override val layoutID = -1

    class TitleViewHolder(val binding: WatchHistoryRecyclerViewTitleBinding) :
        BaseViewHolder(binding)

    class CardViewHolder(val binding: WatchHistoryRecyclerViewCardBinding) :
        BaseViewHolder(binding)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            TITLE_ITEM -> TitleViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.watch_history_recycler_view_title,
                    parent,
                    false
                )
            )

            CARD_ITEM -> CardViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.watch_history_recycler_view_card,
                    parent,
                    false
                )

            )

            else -> throw Exception("item not found")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        when (holder) {
            is TitleViewHolder -> bindTitle(holder, items[position])
            is CardViewHolder -> bindCard(holder, items[position])
            else -> throw Exception("recycler item not found")
        }
    }

    private fun bindCard(holder: CardViewHolder, item: WatchHistoryRecyclerItem) {
        holder.binding.item = (item as WatchHistoryRecyclerItem.CardRecyclerItem).movie
        holder.binding.listener = listener
    }

    private fun bindTitle(holder: TitleViewHolder, item: WatchHistoryRecyclerItem) {
        holder.binding.item = (item as WatchHistoryRecyclerItem.TitleRecyclerItem).title
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is WatchHistoryRecyclerItem.TitleRecyclerItem -> TITLE_ITEM
            is WatchHistoryRecyclerItem.CardRecyclerItem -> CARD_ITEM
        }
    }


    override fun setItems(newItems: List<WatchHistoryRecyclerItem>) {
        items = newItems
        super.setItems(newItems)
    }


    private companion object {

        const val TITLE_ITEM = 21
        const val CARD_ITEM = 14
    }

}