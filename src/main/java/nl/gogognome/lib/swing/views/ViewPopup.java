package nl.gogognome.lib.swing.views;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPopupMenu;
import javax.swing.MenuSelectionManager;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import nl.gogognome.lib.swing.SwingUtils;

/**
 * This class shows a view in a popup.
 */
public class ViewPopup {

	private View view;

	private JPopupMenu popup;

	private PopupMenuListener popupMenuListener;

	public ViewPopup(View view) {
		this.view = view;
	}

	public void show(Component owner, Point p) {
		if (popup == null) {
			initView();
			JPopupMenu popup = new JPopupMenu();
			popup.add(view);
			popup.show(SwingUtils.getTopLevelContainer(owner), p.x, p.y);
			popupMenuListener = new PopupMenuListenerImpl();
			popup.addPopupMenuListener(popupMenuListener);
		}
	}

	public void hidePopup() {
		if (popup != null) {
			requestClose();
		}
	}

	private void initView() {
        Action closeAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent event) {
                requestClose();
            }
        };

        view.setCloseAction(closeAction);
        view.doInit();
	}

	private void requestClose() {
		view.doClose();
		MenuSelectionManager.defaultManager().clearSelectedPath();
		popup = null;
	}

	private void onClosedByUser() {
		view.doClose();
		popup = null;
	}

	private class PopupMenuListenerImpl implements PopupMenuListener {

		@Override
		public void popupMenuWillBecomeVisible(PopupMenuEvent popupmenuevent) {
		}

		@Override
		public void popupMenuWillBecomeInvisible(PopupMenuEvent popupmenuevent) {
			onClosedByUser();
		}

		@Override
		public void popupMenuCanceled(PopupMenuEvent popupmenuevent) {
		}

	}
}
