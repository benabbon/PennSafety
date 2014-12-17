package com.android.pennaed.test.instructionVideo;

import android.content.Intent;
import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.pennaed.InstructionVideo.InstructionVideo;
import com.android.pennaed.R;
import com.android.pennaed.outOfReach.LocationReceiver;
import com.google.android.gms.location.LocationClient;

import junit.framework.TestCase;

/**
 * Created by anand on 12/16/14.
 */
public class InstructionVideoTest extends TestCase {
    private InstructionVideo video;
    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        video = new InstructionVideo();
    }
    @SmallTest
    public void testSrcPath()
    {
        boolean flag = false;
        String srcPath = "rtsp://r1---sn-jc47eu7e.c.youtube.com/CiILENy73wIaGQkOdxPwhfjKmRMYESARFEgGUgZ2aWRlb3MM/0/0/0/video.3gp";
        if(video.getSrcPath() != null) {
            flag = true;
        }
        assertTrue(flag);
        assertEquals(video.getSrcPath(),srcPath);
    }

    @SmallTest
    public void testVideoView()
    {
        boolean flag = false;
        if(video.getVideoview() == null) {
            flag = true;
        }
        assertTrue(flag);

    }




}
