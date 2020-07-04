package ca.bcit.comp2613.bookstore.data;


public class Book {

	private int bookID;
	private String isbn;
	private String authors;
	private int originalPubYear;
	private String originalTitle;
	private float avgRating;
	private long ratingsCount;
	private String imageURL;

	public static class Builder {

		private int bookID;
		private String isbn;
		private String authors;
		private int originalPubYear;
		private String originalTitle;
		private float avgRating;
		private long ratingsCount;
		private String imageURL;

		public Builder(final int bookID, final String originalTitle) {
			this.bookID = bookID;
			this.originalTitle = originalTitle;

		}

		public Builder isbn(final String var) {
			if (!var.isEmpty() && var != null) {
				isbn = var;
			}
			return this;
		}

		public Builder authors(final String var) {
			if (!var.isEmpty() && var != null) {
				authors = var;
			}
			return this;
		}

		public Builder originalPubYear(final int var) {
			originalPubYear = var;
			return this;
		}

		public Builder avgRating(final float var) {
			avgRating = var;
			return this;
		}

		public Builder ratingsCount(final long var) {

			ratingsCount = var;
			return this;
		}

		public Builder imageURL(final String var) {
			if (!var.isEmpty() && var != null) {
				imageURL = var;
			}
			return this;
		}

		public Book build() {
			return new Book(this);
		}

	}

	public Book(final Builder builder) {
		bookID = builder.bookID;
		isbn = builder.isbn;
		authors = builder.authors;
		originalPubYear = builder.originalPubYear;
		originalTitle = builder.originalTitle;
		avgRating = builder.avgRating;
		ratingsCount = builder.ratingsCount;
		imageURL = builder.imageURL;
	}
	
	@Override
	public String toString() {
		return "Book [bookID=" + bookID + ", isbn=" + isbn + ", authors=" + authors + ", originalPubYear="
				+ originalPubYear + ", originalTitle=" + originalTitle + ", avgRating=" + avgRating + ", ratingsCount="
				+ ratingsCount + ", imageURL=" + imageURL + "]";
	}
	
	// getters -------------------------------------------------

	public int getBookID() {
		return bookID;
	}

	public String getIsbn() {
		return isbn;
	}

	public String getAuthors() {
		return authors;
	}

	public int getOriginalPubYear() {
		return originalPubYear;
	}

	public String getOriginalTitle() {
		return originalTitle;
	}

	public float getAvgRating() {
		return avgRating;
	}

	public long getRatingsCount() {
		return ratingsCount;
	}

	public String getImageURL() {
		return imageURL;
	}
	
	// setters -------------------------------------------------

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public void setAuthors(String authors) {
		this.authors = authors;
	}

	public void setOriginalPubYear(int originalPubYear) {
		this.originalPubYear = originalPubYear;
	}

	public void setOriginalTitle(String originalTitle) {
		this.originalTitle = originalTitle;
	}

	public void setAvgRating(float avgRating) {
		this.avgRating = avgRating;
	}

	public void setRatingsCount(long ratingsCount) {
		this.ratingsCount = ratingsCount;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	

}
