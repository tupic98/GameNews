package com.alvarenga.gamenews.fragments

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.support.v7.widget.SearchView
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.TextView
import com.alvarenga.gamenews.NewsDetailActivity
import com.alvarenga.gamenews.R
import com.alvarenga.gamenews.adapters.NewsAdapter
import com.alvarenga.gamenews.db.entity.NewsEntity
import com.alvarenga.gamenews.db.viewmodels.NewsViewModel

@SuppressLint("StaticFieldLeak")
class NewsFragment(): Fragment() {
    private lateinit var newsVM:NewsViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var logintoken:String
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var mLayoutManagerState:Parcelable
    private var fragmentType:Int = 0
    private lateinit var category:String
    private lateinit var searchView:SearchView

    companion object {
        private var THEREST = 0
        private var GAMES = 1
        private var FAVORITE = 2
    }

    fun newInstance(type: Int, vararg categories: String): NewsFragment {
        val args = Bundle()
        val fragment = NewsFragment()
        args.putInt("fragmentType",type)
        if(categories.size > 0){
            args.putString("category", categories[0])
        }
        fragment.arguments = args
        return fragment
    }

    override fun onPause() {
        super.onPause()
        mLayoutManagerState = recyclerView.layoutManager.onSaveInstanceState()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newsVM = ViewModelProviders.of(this).get(NewsViewModel::class.java)
        var sharedPreferences:SharedPreferences = activity!!.getSharedPreferences("Login",Context.MODE_PRIVATE)
        logintoken = sharedPreferences.getString("token","")
        setHasOptionsMenu(true)
        fragmentType = arguments!!.getInt("fragmentType")
        if(arguments!!.getString("category") != null){
            category = arguments!!.getString("category")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view:View = inflater.inflate(R.layout.news_fragment,container,false)
        recyclerView = view.findViewById(R.id.NewsRecyclerView)
        gridLayoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int
                    = if (position % 3 == 0) 2 else 1
        }
        newsAdapter = object : NewsAdapter() {
            override fun onClickFav(view: View, id: String, currentFav: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onClickCardView(titulo: String, description: String, content: String, image: String) {
                val intent:Intent = Intent(context,NewsDetailActivity::class.java)
                intent.putExtra("title",titulo)
                intent.putExtra("description",description)
                intent.putExtra("content",content)
                intent.putExtra("image",image)
                context!!.startActivity(intent)
            }
        }
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.adapter = newsAdapter
        newsVM.getAllNews().observe(this,Observer<List<NewsEntity>>{t -> setNewsOnAdapter(t!!) })
        if (savedInstanceState != null) {
            mLayoutManagerState = savedInstanceState.getParcelable("LAYOUT_MANAGER_STATE")
            recyclerView.layoutManager.onRestoreInstanceState(mLayoutManagerState)
        }
        return view
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("LAYOUT_MANAGER_STATE", mLayoutManagerState)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater!!.inflate(R.menu.main, menu)
        val item: MenuItem = menu!!.findItem(R.id.search_action)
        searchView = item.actionView as SearchView
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                getNews(query!!)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                getNews(newText!!)
                return true
            }
        })
        SearchViewSetUp()
    }
    private fun SearchViewSetUp() {
        val searchText = searchView.findViewById<EditText>(android.support.v7.appcompat.R.id.search_src_text)
        searchText.hint = "Search"
        searchText.setHintTextColor(Color.LTGRAY)
        searchText.setTextColor(resources.getColor(R.color.white))
        val searchTextView = searchView.findViewById<AutoCompleteTextView>(android.support.v7.appcompat.R.id.search_src_text)
        try {
            val mCursorDrawableRes = TextView::class.java.getDeclaredField("mCursorDrawableRes")
            mCursorDrawableRes.isAccessible = true
            mCursorDrawableRes.set(searchTextView, 0) //This sets the cursor resource ID to 0 or @null which will make it visible on white background
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun getNews(query:String){
        val query = "%$query%"
        newsVM.getNewsByQuery(query).observe(this, Observer<List<NewsEntity>> { t -> setNewsOnAdapter(t!!) })
    }
    private fun setNewsOnAdapter(news:List<NewsEntity>){
        when (fragmentType) {
            FAVORITE -> {
                val favorites:ArrayList<NewsEntity> = ArrayList()
                for(new in news){
                    if(new.favorite == 1){
                        favorites.add(new)
                    }
                }
                newsAdapter.setNews(favorites)
            }
            GAMES -> {
                val games:ArrayList<NewsEntity> = ArrayList()
                for(game in news){
                    if(game.game == this.category){
                        games.add(game)
                    }
                }
                newsAdapter.setNews(games)
            }
            else -> newsAdapter.setNews(news)
        }
    }
}