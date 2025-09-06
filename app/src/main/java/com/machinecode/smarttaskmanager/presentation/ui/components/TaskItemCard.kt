package com.machinecode.smarttaskmanager.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.machinecode.smarttaskmanager.domain.Task

@Composable
fun TaskItemCard(
    repo: Task, onClick: () -> Unit
) {
    val textColor = MaterialTheme.colorScheme.onSurface

    Card(
        modifier = Modifier
            .testTag("ttRepositoryItem")
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp),
    ) {
        Row(modifier = Modifier.padding(12.dp)) {
//            AsyncImage(
//                model = repo.owner.avatarUrl,
//                contentDescription = null,
//                modifier = Modifier
//                    .size(48.dp)
//                    .clip(CircleShape)
//            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = repo.title,
                    style = MaterialTheme.typography.titleSmall,
                    color = textColor
                )
                Text(
                    text = repo.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = textColor
                )
                Text(
                    text = repo.description.toString(),
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = textColor
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
//                    if (repo.stargazersCountStr.isNotEmpty()) {
//                        Row(
//                            verticalAlignment = Alignment.CenterVertically,
//                            horizontalArrangement = Arrangement.spacedBy(6.dp)
//                        ) {
//                            Icon(
//                                Icons.Default.StarOutline,
//                                contentDescription = "Stars",
//                                tint = if (isSystemInDarkTheme()) Color(0xFFFFD700) else Color(
//                                    0xFFDAA520
//                                ),
//                                modifier = Modifier.size(14.dp)
//                            )
//                            Text(
//                                text = repo.stargazersCountStr.toString(),
//                                color = textColor
//                            )
//                        }
//                    }

//                    if (repo.languageColor != null) {
//                        Row(
//                            verticalAlignment = Alignment.CenterVertically,
//                            horizontalArrangement = Arrangement.spacedBy(6.dp)
//                        ) {
//                            Box(
//                                modifier = Modifier
//                                    .size(10.dp)
//                                    .clip(CircleShape)
//                                    .background(repo.languageColor)
//                            )
//                            Text(
//                                text = repo.language,
//                                color = textColor
//                            )
//                        }
//                    }
                }
            }
        }
    }
}