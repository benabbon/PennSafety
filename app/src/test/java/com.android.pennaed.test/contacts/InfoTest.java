import android.test.ActivityUnitTestCase;

import com.android.pennaed.contacts.Info;
import com.android.pennaed.R;

public class InfoTest extends ActivityUnitTestCase<Info> {
	private Info info;
	public InfoTest() {
		super(Info.class);
	}

	public void setUp() {
		super.setUp();
		Intent intent = new Intent(getInstrumentation().getTargetContext(), Info.class);
		startActivity(intent, null, null);
		info = getActivity();
	}

	public void testNonNullViews() {
		int tvTitle = R.id.tvTitle;
		assertNotNull(info.findViewById(tvTitle));
		int tvInfo = R.id.tvInfo;
		assertNotNull(info.findViewById(tvInfo));
	}

}