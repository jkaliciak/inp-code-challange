package pl.inpost.recruitmenttask.shipments.ui.shipmentlist.composable

import androidx.compose.animation.animateContentSize
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun <T> SwipeToDismissContainer(
    isDismissEnabled: Boolean,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    content: @Composable () -> Unit
) {
    val swipeState = rememberSwipeToDismissBoxState()

    SwipeToDismissBox(
        modifier = modifier.animateContentSize(),
        state = swipeState,
        backgroundContent = { ArchiveBackground(swipeDismissState = swipeState) },
        enableDismissFromStartToEnd = false,
        enableDismissFromEndToStart = isDismissEnabled,
        gesturesEnabled = isDismissEnabled,
    ) {
        content()
    }

    when (swipeState.currentValue) {
        SwipeToDismissBoxValue.EndToStart -> onDismiss()
        SwipeToDismissBoxValue.StartToEnd -> {}
        SwipeToDismissBoxValue.Settled -> {}
    }
}