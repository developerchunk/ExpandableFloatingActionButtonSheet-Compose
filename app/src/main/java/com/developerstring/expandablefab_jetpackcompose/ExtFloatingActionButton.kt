package com.developerstring.expandablefab_jetpackcompose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.developerstring.expandablefab_jetpackcompose.ui.theme.Pink80

// The coding and documentation of this file is done from a perspective of learning

/**This data class holds 2 variables icon and text which is required by [CustomExpandableFAB] to show the item in expanded form or for the button*/
data class FABItem(
    val icon: ImageVector,
    val text: String,
)

/**This [CustomExpandableFAB] creates the UI for the FAB, a Material-3 looking Floating Action Button in Jetpack Compose that expands into a Sheet with multiple options when clicked.
 * - This composable function should be called in an floatingActionButton(value-parameter) inside of a Scaffold */
@Composable
fun CustomExpandableFAB(
    modifier: Modifier = Modifier,
    items: List<FABItem>,
    fabButton: FABItem = FABItem(icon = Icons.Rounded.Add, text = "Expanded"),
    onItemClick: (FABItem) -> Unit
) {

    var buttonClicked by remember {
        mutableStateOf(false)
    }

    val interactionSource = MutableInteractionSource()

    Card(
        modifier = modifier,
        elevation = CardDefaults.elevatedCardElevation(4.dp),
        shape = RoundedCornerShape(30.dp)
    ) {

        // parent layout
        Column {

//            you can also use the spring() in EnterTransition/ExitTransition provided by Material-3 library for a more smooth animation, but it increases the collapse time of the sheet/FAB
//            example - spring(dampingRatio = 3f)

            // The Expandable Sheet layout
            AnimatedVisibility(
                visible = buttonClicked,
                enter = expandVertically(tween(1500)) + fadeIn(),
                exit = shrinkVertically(tween(1200)) + fadeOut(
                    animationSpec = tween(1000)
                )
            ) {
                // display the items
                Column(
                    modifier = Modifier
                        .padding(vertical = 20.dp, horizontal = 30.dp)
                ) {
                    items.forEach { item ->
                        Row(modifier = Modifier
                            .padding(vertical = 10.dp)
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null,
                                onClick = {
                                    onItemClick(item)
                                    buttonClicked = false
                                }
                            )) {
                            Icon(
                                imageVector = item.icon, contentDescription = "refresh"
                            )

                            Spacer(modifier = Modifier.width(15.dp))

                            Text(text = item.text)
                        }
                    }
                }
            }

            // The FAB main button
            Card(
                modifier = Modifier.clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = {
                        buttonClicked = !buttonClicked
                    }
                ),
                colors = CardDefaults.cardColors(Pink80),
                shape = RoundedCornerShape(30.dp)
            ) {
                Row(
                    modifier = Modifier.padding(vertical = 20.dp, horizontal = 30.dp)
                ) {
                    Icon(
                        imageVector = fabButton.icon, contentDescription = "refresh"
                    )
                    AnimatedVisibility(
                        visible = buttonClicked,
                        enter = expandVertically(animationSpec = tween(1500)) + fadeIn(),
                        exit = shrinkVertically(tween(1200)) + fadeOut(tween(1200))
                    ) {
                        Row {
                            Spacer(modifier = Modifier.width(20.dp))
                            Text(text = fabButton.text)
                        }
                    }
                }
            }

        }

    }

}