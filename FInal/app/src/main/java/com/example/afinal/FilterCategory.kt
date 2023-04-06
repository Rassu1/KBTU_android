package com.example.afinal

import android.widget.Filter

class FilterCategory : Filter {

    private val filterlist: ArrayList<ModelCategory>

    private val adapterCategory: AdapterCategory

    constructor(filterlist: ArrayList<ModelCategory>, adapterCategory: AdapterCategory) : super() {
        this.filterlist = filterlist
        this.adapterCategory = adapterCategory
    }

    override fun performFiltering(constraint: CharSequence?): FilterResults {
        var constraint = constraint
        val results = FilterResults()

        if (constraint != null && constraint.isNotEmpty()){

            constraint = constraint.toString().uppercase()
            val filterModels: ArrayList<ModelCategory> = ArrayList()

            for (i in 0 until filterlist.size) {
                if( filterlist[i].category.uppercase().contains(constraint)){
                    filterModels.add(filterlist[i])
                }
            }
            results.count = filterModels.size
            results.values = filterModels
        }
        else{
            results.count = filterlist.size
            results.values = filterlist
        }
        return results
    }

    override fun publishResults(constraint: CharSequence?, results: FilterResults) {
        adapterCategory.categoryArrayList = results.values as ArrayList<ModelCategory>

        adapterCategory.notifyDataSetChanged()
    }
}