package br.com.tmsfasdom.appdenize;

/**
 * Created by Denize on 02/11/2015.
 */


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import com.google.android.gms.vision.barcode.Barcode;

//import com.google.android.gms.samples.vision.barcodereader.ui.camera.GraphicOverlay;

/**
 * Graphic instance for rendering barcode position, size, and ID within an associated graphic
 * overlay view.
 */
public class BarcodeGraphic extends GraphicOverlay.Graphic {

    private static final int[] COLOR_CHOICES = {
            Color.BLUE,
            Color.RED,
            Color.GREEN
    };
    private int mId;
    private Paint mRectPaint;
    private Paint mTextPaint;
    private volatile Barcode mBarcode;

    BarcodeGraphic(GraphicOverlay overlay) {
        super(overlay);
        mRectPaint = new Paint();
        mRectPaint.setStyle(Paint.Style.STROKE);
        mRectPaint.setStrokeWidth(4.0f);
        mTextPaint = new Paint();
        mTextPaint.setTextSize(36.0f);
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public Barcode getBarcode() {
        return mBarcode;
    }

    /**
     * Updates the barcode instance from the detection of the most recent frame.  Invalidates the
     * relevant portions of the overlay to trigger a redraw.
     */
    void updateItem(Barcode barcode) {
        mBarcode = barcode;
        postInvalidate();
    }

    /**
     * Draws the barcode annotations for position, size, and raw value on the supplied canvas.
     */
    @Override
    public void draw(Canvas canvas) {
        Barcode barcode = mBarcode;
        if (barcode == null) {
            return;
        }
        int selectedColor;
        if (this.mBarcode.displayValue.length() == 44) {
            selectedColor = Color.BLUE;
        } else {
            selectedColor = Color.RED;
        }
        mRectPaint.setColor(selectedColor);
        mTextPaint.setColor(selectedColor);

        // Draws the bounding box around the barcode.
        RectF rect = new RectF(barcode.getBoundingBox());
        rect.left = translateX(rect.left);
        rect.top = translateY(rect.top);
        rect.right = translateX(rect.right);
        rect.bottom = translateY(rect.bottom);
        canvas.drawRect(rect, mRectPaint);

        // Draws a label at the bottom of the barcode indicate the barcode value that was detected.
        canvas.drawText(barcode.rawValue, rect.left, rect.bottom, mTextPaint);
    }
}