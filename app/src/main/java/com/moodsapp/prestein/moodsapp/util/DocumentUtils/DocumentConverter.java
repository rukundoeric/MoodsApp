package com.moodsapp.prestein.moodsapp.util.DocumentUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.ParcelFileDescriptor;

import java.io.File;

public class DocumentConverter {
    private Context context;

    public DocumentConverter(Context context) {
        this.context = context;
    }

    public   Bitmap pdfToBitmap(File pdfFile) {
        Bitmap bitmaps = null;
        try {
            PdfRenderer renderer = new PdfRenderer(ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY));
            Bitmap bitmap;
            final int pageCount = renderer.getPageCount();
            PdfRenderer.Page page = renderer.openPage(0);
            int width = context.getResources().getDisplayMetrics().densityDpi / 72 * page.getWidth();
            int height = context.getResources().getDisplayMetrics().densityDpi / 72 * page.getWidth();
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
            bitmaps=bitmap;
            // close the page
            page.close();
            renderer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return bitmaps;

    }
}
