package com.johneas.android.movie.view.movies.list.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.johneas.android.movie.R;
import com.johneas.android.movie.data.model.Movie;
import com.johneas.android.movie.view.common.utils.Constants;
import com.johneas.android.movie.view.common.interfaces.ListItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.movieViewHolder> {

	private static final String TAG = MovieAdapter.class.getSimpleName();
	private List<com.johneas.android.movie.data.model.Movie> movieList;
	private ListItemClickListener clickListener;
	private Context context;


	class movieViewHolder
			extends RecyclerView.ViewHolder
			implements OnClickListener {
		@BindView(R.id.grid_movie_poster_iv)
		PosterImageView displayMoviePoster;
		@BindView(R.id.tvMovieName)
		TextView tvMovieName;

		public movieViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
			itemView.setOnClickListener(this);
		}

		@Override
		public void onClick(View view) {
			int clickedPosition = getAdapterPosition();
			int movieId = movieList.get(clickedPosition).getId();
			clickListener.onListItemClick(movieId);
		}
	}

	public void setOnItemClickListener(ListItemClickListener onItemClickListener) {
		this.clickListener = onItemClickListener;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public MovieAdapter() {
		this.movieList = new ArrayList<>();
	}

	@Override
	public movieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		Context context = parent.getContext();
		boolean shouldAttachToParentImmediately = false;

		View itemView = LayoutInflater.from(context).inflate(
						R.layout.list_item,
						parent,
						shouldAttachToParentImmediately);

		return new movieViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(movieViewHolder holder, int position) {
		Movie movieAtScrollPosition = this.movieList.get(position);
		String posterPath = movieAtScrollPosition.getPosterPath();

		holder.tvMovieName.setText(movieAtScrollPosition.getTitle());

		if (posterPath == null) {
			holder.displayMoviePoster.setImageResource(R.drawable.ic_movie);
			return;
		}

		String moviePosterUrl = Constants.POSTER_IMAGE_BASE_URL +
				Constants.POSTER_IMAGE_SIZE + posterPath;

		Picasso.with(context)
				.load(moviePosterUrl)
				.placeholder(R.drawable.ic_autorenew)
				.error(R.drawable.ic_autorenew)
				.into(holder.displayMoviePoster);
	}

	@Override
	public int getItemCount() {
		return this.movieList.size();
	}

	public void clear() {
		this.movieList = new ArrayList<>();
		notifyDataSetChanged();
	}

	public void addAll(List<Movie> movies) {
		if (movies != null && !movies.isEmpty()) {
			for (Movie movie : movies) {
				this.movieList.add(movie);
			}
			notifyDataSetChanged();
		}
	}
}
