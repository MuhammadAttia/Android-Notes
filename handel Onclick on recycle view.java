/*
*HOW I DO RECYCLERVIEWS CLICK EVENTS

In a given project, I am sure there will be more than one instances where I will need to use recyclerviews and so I will need to handle click events! So, to have a solution that can be reused, I normally create a class:
*/
//RecyclerItemClickListener.java
 
 public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
 
   private GestureDetector mGestureDetector;
   private OnItemClickListener mListener;
   
 
   public interface OnItemClickListener {
      void onItemClick(View view, int position);
      void onItemLongClick(View view, int position);
 
   }
 
   public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, OnItemClickListener listener)
   {
       mListener = listener;
 
       mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener()
       {
         @Override
         public boolean onSingleTapUp(MotionEvent e)
         {
             return true;
         }
 
         @Override
         public void onLongPress(MotionEvent e){
             View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
 
            if(childView != null && mListener != null){
                mListener.onItemLongClick(childView, recyclerView.getChildAdapterPosition(childView));
            }
         }
      });
   }
 
   @Override
   public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e)
   {
     View childView = view.findChildViewUnder(e.getX(), e.getY());
 
     if(childView != null && mListener != null && mGestureDetector.onTouchEvent(e)){
        mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
     }
 
     return false;
   }
 
   @Override
   public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {}
 
   @Override
   public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
 
   }
}
 
 
//Now, we are ready to implement this code in our recyclerview reference; could be inside your fragment or activity! Here is how you do that:




   mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, mRecyclerView, new RecyclerItemClickListener
            .OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            //handle click events here

          int itemPosition = recyclerView.getChildLayoutPosition(view);
               // if i made arraylist of items 
                Uri link = Uri.parse(friendslist.get(itemPosition).getUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW,link);
                startActivity(intent);

                // if i made arrylist of objects 
                //handle click events here
                int itemPosition = recyclerView.getChildLayoutPosition(view);
                News news =(News)newsArrayList.get(itemPosition);
                Uri link = Uri.parse(news.getUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW,link);
                startActivity(intent);

        }

        @Override
        public void onItemLongClick(View view, int position) {
            //handle longClick if any
        }
   }));
