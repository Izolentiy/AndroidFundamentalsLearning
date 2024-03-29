package org.izolentiy.movieslist;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHoldver> {

    private final List<Movie> movies;
    private final Listener onMovieClickListener;

    public MoviesAdapter(List<Movie> movies, Listener onMovieClickListener) {
//    public MoviesAdapter(List<Movie> movies) {
        this.movies = movies;
        this.onMovieClickListener = onMovieClickListener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_item, viewGroup, false);
        view.setOnClickListener(v -> onMovieClickListener.onMovieClick((Movie) v.getTag()));
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder viewHolder, final int i) {
        Movie movie = movies.get(i);
        viewHolder.bind(movie);
        viewHolder.itemView.setTag(movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    static final class MovieViewHolder extends RecyclerView.ViewHolder {

        private final TextView nameTextView;
        private final TextView descriptionTextView;
        private final ImageView posterImageView;

        private MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.movie_item__tv_name);
            descriptionTextView = itemView.findViewById(R.id.movie_item__tv_description);
            posterImageView = itemView.findViewById(R.id.movie_item__iv_poster);
        }

        private void bind(@NonNull Movie movie) {
            nameTextView.setText(movie.name);
            descriptionTextView.setText(movie.description);
            posterImageView.setImageResource(movie.poster);
        }

    }

    interface Listener {

        void onMovieClick(Movie movie);

    }

}